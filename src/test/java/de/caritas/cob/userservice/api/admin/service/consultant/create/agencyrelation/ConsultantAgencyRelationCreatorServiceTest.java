package de.caritas.cob.userservice.api.admin.service.consultant.create.agencyrelation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.caritas.cob.userservice.api.adapters.keycloak.KeycloakService;
import de.caritas.cob.userservice.api.adapters.web.dto.AgencyDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.CreateConsultantAgencyDTO;
import de.caritas.cob.userservice.api.manager.consultingtype.ConsultingTypeManager;
import de.caritas.cob.userservice.api.model.Consultant;
import de.caritas.cob.userservice.api.model.ConsultantAgencyStatus;
import de.caritas.cob.userservice.api.model.ConsultantStatus;
import de.caritas.cob.userservice.api.port.out.ConsultantAgencyRepository;
import de.caritas.cob.userservice.api.port.out.ConsultantRepository;
import de.caritas.cob.userservice.api.service.ConsultantAgencyService;
import de.caritas.cob.userservice.api.service.agency.AgencyService;
import java.util.Collections;
import java.util.Optional;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsultantAgencyRelationCreatorServiceTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private ConsultantAgencyRelationCreatorService consultantAgencyRelationCreatorService;

  @Mock private ConsultantAgencyService consultantAgencyService;

  @Mock private ConsultantRepository consultantRepository;

  @Mock private AgencyService agencyService;

  @Mock private KeycloakService keycloakService;

  @Mock private RocketChatAsyncHelper rocketChatAsyncHelper;

  @Mock private ConsultingTypeManager consultingTypeManager;

  @Mock private ConsultantAgencyRepository consultantAgencyRepository;

  @Test
  void
      createNewConsultantAgency_Should_notThrowNullPointerException_When_agencyTypeIsU25AndConsultantHasNoAgencyAssigned() {
    AgencyDTO agencyDTO = new AgencyDTO().consultingType(1).id(2L);

    when(this.consultantRepository.findByIdAndDeleteDateIsNull(anyString()))
        .thenReturn(Optional.of(new Consultant()));
    when(agencyService.getAgencyWithoutCaching(eq(2L))).thenReturn(agencyDTO);

    CreateConsultantAgencyDTO createConsultantAgencyDTO =
        new CreateConsultantAgencyDTO().roleSetKey("valid role set").agencyId(2L);

    final var response =
        easyRandom.nextObject(
            de.caritas.cob.userservice.consultingtypeservice.generated.web.model
                .ExtendedConsultingTypeResponseDTO.class);
    when(consultingTypeManager.getConsultingTypeSettings(1)).thenReturn(response);

    assertDoesNotThrow(
        () ->
            this.consultantAgencyRelationCreatorService.createNewConsultantAgency(
                "consultant Id", createConsultantAgencyDTO));
  }

  @Test
  void updateConsultantStatus_Should_UpdateStatusToCreated_When_NoInProgressAgencies() {
    String consultantId = "test-consultant-id";
    Consultant consultant = new Consultant();
    consultant.setId(consultantId);

    when(consultantRepository.findByIdAndDeleteDateIsNull(consultantId))
        .thenReturn(Optional.of(consultant));

    when(consultantAgencyRepository.findByConsultantIdAndStatusAndDeleteDateIsNull(
            consultantId, ConsultantAgencyStatus.IN_PROGRESS))
        .thenReturn(Collections.emptyList());

    consultantAgencyRelationCreatorService.updateConsultantStatus(consultantId);

    assertThat(consultant.getStatus()).isEqualTo(ConsultantStatus.CREATED);
    verify(consultantRepository).save(consultant);
  }
}
