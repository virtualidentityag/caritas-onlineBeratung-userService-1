package de.caritas.cob.userservice.api.facade;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;
import static org.apache.commons.lang3.StringUtils.isBlank;

import de.caritas.cob.userservice.api.adapters.keycloak.dto.KeycloakCreateUserResponseDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.AgencyDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.UserDTO;
import de.caritas.cob.userservice.api.admin.service.tenant.TenantService;
import de.caritas.cob.userservice.api.config.auth.UserRole;
import de.caritas.cob.userservice.api.exception.httpresponses.InternalServerErrorException;
import de.caritas.cob.userservice.api.facade.rollback.RollbackFacade;
import de.caritas.cob.userservice.api.facade.rollback.RollbackUserAccountInformation;
import de.caritas.cob.userservice.api.helper.AgencyVerifier;
import de.caritas.cob.userservice.api.helper.UserVerifier;
import de.caritas.cob.userservice.api.manager.consultingtype.ConsultingTypeManager;
import de.caritas.cob.userservice.api.model.User;
import de.caritas.cob.userservice.api.port.out.IdentityClient;
import de.caritas.cob.userservice.api.service.agency.AgencyService;
import de.caritas.cob.userservice.api.service.consultingtype.TopicService;
import de.caritas.cob.userservice.api.service.statistics.StatisticsService;
import de.caritas.cob.userservice.api.service.statistics.event.RegistrationStatisticsEvent;
import de.caritas.cob.userservice.api.service.user.UserService;
import de.caritas.cob.userservice.api.tenant.TenantContext;
import de.caritas.cob.userservice.consultingtypeservice.generated.web.model.ExtendedConsultingTypeResponseDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/** Facade to encapsulate the steps to initialize a user account. */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserFacade {
  private final @NonNull UserVerifier userVerifier;
  private final @NonNull IdentityClient identityClient;
  private final @NonNull UserService userService;
  private final @NonNull RollbackFacade rollbackFacade;
  private final @NonNull ConsultingTypeManager consultingTypeManager;
  private final @NonNull AgencyVerifier agencyVerifier;
  private final @NonNull CreateNewConsultingTypeFacade createNewConsultingTypeFacade;
  private final @NonNull StatisticsService statisticsService;
  private final @NonNull TopicService topicService;

  private final @NonNull TenantService tenantService;

  private final @NonNull AgencyService agencyService;

  /**
   * Creates a user in Keycloak and MariaDB. Then creates a session or chat account depending on the
   * provided consulting ID.
   *
   * @param userDTO {@link UserDTO}
   */
  public Long createUserAccountWithInitializedConsultingType(final UserDTO userDTO) {

    userVerifier.checkIfAllRequiredAttributesAreCorrectlyFilled(userDTO);
    userVerifier.checkIfUsernameIsAvailable(userDTO);
    agencyVerifier.checkIfConsultingTypeMatchesToAgency(userDTO);

    KeycloakCreateUserResponseDTO response = identityClient.createKeycloakUser(userDTO);
    var user = updateIdentityAndCreateAccount(response.getUserId(), userDTO, UserRole.USER);
    var consultingTypeSettings = obtainConsultingTypeSettings(userDTO);
    var registration =
        createNewConsultingTypeFacade.initializeNewConsultingType(
            userDTO, user, consultingTypeSettings);

    try {
      RegistrationStatisticsEvent registrationEvent =
          new RegistrationStatisticsEvent(
              userDTO,
              user,
              registration.getSessionId(),
              topicService.findTopicInternalIdentifier(userDTO.getMainTopicId()),
              topicService.findTopicsInternalAttributes(userDTO.getTopicIds()),
              getTenantName(),
              getAgencyName(userDTO));
      statisticsService.fireEvent(registrationEvent);
    } catch (Exception e) {
      log.error("Could not create registration statistics event", e);
    }

    return registration.getSessionId();
  }

  private String getTenantName() {
    de.caritas.cob.userservice.tenantservice.generated.web.model.RestrictedTenantDTO tenant =
        tenantService.getRestrictedTenantData(TenantContext.getCurrentTenant());
    return tenant.getName();
  }

  private String getAgencyName(UserDTO userDTO) {
    if (userDTO.getAgencyId() != null) {
      AgencyDTO agencyWithoutCaching = agencyService.getAgencyWithoutCaching(userDTO.getAgencyId());
      return agencyWithoutCaching.getName();
    } else {
      log.warn(
          "AgencyId is null for user during registration. Will not send agency name to statistics");
      return StringUtils.EMPTY;
    }
  }

  /**
   * Updates Keycloak role and password and creates a user account in MariaDB.
   *
   * @param userId Keycloak user ID
   * @param userDTO {@link UserDTO}
   * @return {@link User}
   */
  public User updateIdentityAndCreateAccount(String userId, UserDTO userDTO, UserRole role) {

    User user = null;
    try {
      updateKeycloakRoleAndPassword(userId, userDTO, role);

      var extendedConsultingTypeResponseDTO =
          consultingTypeManager.getConsultingTypeSettings(userDTO.getConsultingType());
      var language =
          isNull(userDTO.getPreferredLanguage()) ? null : userDTO.getPreferredLanguage().toString();

      user =
          userService.createUser(
              userId,
              null,
              userDTO.getUsername(),
              returnDummyEmailIfNoneGiven(userDTO, userId),
              isTrue(extendedConsultingTypeResponseDTO.getLanguageFormal()),
              language);

    } catch (Exception ex) {
      rollBackAccountInitialization(userId, userDTO);
    }

    return user;
  }

  private ExtendedConsultingTypeResponseDTO obtainConsultingTypeSettings(UserDTO userDTO) {
    return consultingTypeManager.getConsultingTypeSettings(userDTO.getConsultingType());
  }

  private void updateKeycloakRoleAndPassword(String userId, UserDTO userDTO, UserRole role) {
    checkIfUserIdNotNull(userId, userDTO);
    identityClient.updateRole(userId, role);
    identityClient.updatePassword(userId, userDTO.getPassword());
  }

  private void checkIfUserIdNotNull(String userId, UserDTO userDTO) {
    if (isNull(userId)) {
      throw new InternalServerErrorException(
          "Could not create Keycloak account for: %s".formatted(userDTO.toString()));
    }
  }

  private String returnDummyEmailIfNoneGiven(UserDTO userDTO, String userId) {
    if (isBlank(userDTO.getEmail())) {
      return identityClient.updateDummyEmail(userId, userDTO);
    }

    return userDTO.getEmail();
  }

  private void rollBackAccountInitialization(String userId, UserDTO userDTO) {
    rollbackFacade.rollBackUserAccount(
        RollbackUserAccountInformation.builder()
            .userId(userId)
            .rollBackUserAccount(Boolean.parseBoolean(userDTO.getTermsAccepted()))
            .build());
    throw new InternalServerErrorException(
        "Could not update account data on registration for: %s".formatted(userDTO));
  }
}
