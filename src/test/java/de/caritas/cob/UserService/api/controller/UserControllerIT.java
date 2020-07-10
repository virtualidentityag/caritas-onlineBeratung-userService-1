package de.caritas.cob.UserService.api.controller;

import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_ACCEPT_ENQUIRY;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_CREATE_ENQUIRY_MESSAGE;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_CHAT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_CHAT_MEMBERS;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_CHAT_MEMBERS_WITH_INVALID_PATH_PARAMS;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_CHAT_WITH_INVALID_PATH_PARAMS;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_CONSULTANTS_FOR_AGENCY;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_CONSULTANTS_FOR_AGENCY_WITHOUT_PARAM;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_COUNT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_OFFSET;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_STATUS;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_INVALID_FILTER;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_NEGATIVE_COUNT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_NEGATIVE_OFFSET;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_SESSIONS_FOR_AUTHENTICATED_USER;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_COUNT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_OFFSET;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_INVALID_FILTER;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_NEGATIVE_COUNT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_NEGATIVE_OFFSET;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_POST_CHAT_NEW;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_ASSIGN_SESSION;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_ASSIGN_SESSION_INVALID_PARAMS;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_CHAT_START;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_CHAT_START_WITH_INVALID_PATH_PARAMS;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_CHAT_STOP;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_CHAT_STOP_INVALID;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_CONSULTANT_ABSENT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_JOIN_CHAT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_JOIN_CHAT_WITH_INVALID_PATH_PARAMS;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_UPDATE_CHAT;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_UPDATE_CHAT_INVALID_PATH_PARAMS;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_PUT_UPDATE_PASSWORD;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_REGISTER_USER;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_SEND_NEW_MESSAGE_NOTIFICATION;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_UPDATE_KEY;
import static de.caritas.cob.UserService.testHelper.PathConstants.PATH_USER_DATA;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.INVALID_U25_USER_REQUEST_BODY_AGE;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.INVALID_U25_USER_REQUEST_BODY_STATE;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.INVALID_USER_REQUEST_BODY;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.USER_REQUEST_BODY_WITH_USERNAME_TOO_LONG;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.USER_REQUEST_BODY_WITH_USERNAME_TOO_SHORT;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.VALID_CREATE_CHAT_BODY;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.VALID_U25_USER_REQUEST_BODY;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.VALID_UPDATE_CHAT_BODY;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.VALID_USER_REQUEST_BODY;
import static de.caritas.cob.UserService.testHelper.RequestBodyConstants.VALID_USER_REQUEST_BODY_WITH_ENCODED_PASSWORD;
import static de.caritas.cob.UserService.testHelper.TestConstants.ABSENCE_MESSAGE;
import static de.caritas.cob.UserService.testHelper.TestConstants.AGENCY_ID;
import static de.caritas.cob.UserService.testHelper.TestConstants.CITY;
import static de.caritas.cob.UserService.testHelper.TestConstants.CONSULTANT_ID;
import static de.caritas.cob.UserService.testHelper.TestConstants.CONSULTING_TYPE_SETTINGS_U25;
import static de.caritas.cob.UserService.testHelper.TestConstants.CONSULTING_TYPE_SETTINGS_WITHOUT_MANDATORY_FIELDS;
import static de.caritas.cob.UserService.testHelper.TestConstants.CONSULTING_TYPE_SETTINGS_WITH_MANDATORY_FIELDS;
import static de.caritas.cob.UserService.testHelper.TestConstants.CONSULTING_TYPE_SUCHT;
import static de.caritas.cob.UserService.testHelper.TestConstants.CONSULTING_TYPE_U25;
import static de.caritas.cob.UserService.testHelper.TestConstants.COUNT_10;
import static de.caritas.cob.UserService.testHelper.TestConstants.CREATE_CHAT_RESPONSE_DTO;
import static de.caritas.cob.UserService.testHelper.TestConstants.DECODED_PASSWORD;
import static de.caritas.cob.UserService.testHelper.TestConstants.DESCRIPTION;
import static de.caritas.cob.UserService.testHelper.TestConstants.INACTIVE_CHAT;
import static de.caritas.cob.UserService.testHelper.TestConstants.IS_ABSENT;
import static de.caritas.cob.UserService.testHelper.TestConstants.IS_MONITORING;
import static de.caritas.cob.UserService.testHelper.TestConstants.IS_NO_TEAM_SESSION;
import static de.caritas.cob.UserService.testHelper.TestConstants.IS_TEAM_SESSION;
import static de.caritas.cob.UserService.testHelper.TestConstants.MASTER_KEY_1;
import static de.caritas.cob.UserService.testHelper.TestConstants.MASTER_KEY_DTO_KEY_1;
import static de.caritas.cob.UserService.testHelper.TestConstants.MASTER_KEY_DTO_KEY_2;
import static de.caritas.cob.UserService.testHelper.TestConstants.NAME;
import static de.caritas.cob.UserService.testHelper.TestConstants.OFFSET_0;
import static de.caritas.cob.UserService.testHelper.TestConstants.POSTCODE;
import static de.caritas.cob.UserService.testHelper.TestConstants.RC_GROUP_ID;
import static de.caritas.cob.UserService.testHelper.TestConstants.RC_TOKEN;
import static de.caritas.cob.UserService.testHelper.TestConstants.RC_TOKEN_HEADER_PARAMETER_NAME;
import static de.caritas.cob.UserService.testHelper.TestConstants.RC_USER_ID;
import static de.caritas.cob.UserService.testHelper.TestConstants.RC_USER_ID_HEADER_PARAMETER_NAME;
import static de.caritas.cob.UserService.testHelper.TestConstants.ROCKETCHAT_ID;
import static de.caritas.cob.UserService.testHelper.TestConstants.SESSION_FILTER_ALL;
import static de.caritas.cob.UserService.testHelper.TestConstants.SESSION_ID;
import static de.caritas.cob.UserService.testHelper.TestConstants.SESSION_STATUS_NEW;
import static de.caritas.cob.UserService.testHelper.TestConstants.USERNAME;
import static de.caritas.cob.UserService.testHelper.TestConstants.USER_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.InternalServerErrorException;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.UserService.api.authorization.Authority;
import de.caritas.cob.UserService.api.authorization.UserRole;
import de.caritas.cob.UserService.api.facade.AssignSessionFacade;
import de.caritas.cob.UserService.api.facade.CreateChatFacade;
import de.caritas.cob.UserService.api.facade.CreateEnquiryMessageFacade;
import de.caritas.cob.UserService.api.facade.CreateUserFacade;
import de.caritas.cob.UserService.api.facade.DeleteUserFacade;
import de.caritas.cob.UserService.api.facade.EmailNotificationFacade;
import de.caritas.cob.UserService.api.facade.GetChatFacade;
import de.caritas.cob.UserService.api.facade.GetChatMembersFacade;
import de.caritas.cob.UserService.api.facade.GetSessionListFacade;
import de.caritas.cob.UserService.api.facade.GetUserDataFacade;
import de.caritas.cob.UserService.api.facade.JoinAndLeaveChatFacade;
import de.caritas.cob.UserService.api.facade.StartChatFacade;
import de.caritas.cob.UserService.api.facade.StopChatFacade;
import de.caritas.cob.UserService.api.helper.AuthenticatedUser;
import de.caritas.cob.UserService.api.helper.AuthenticatedUserHelper;
import de.caritas.cob.UserService.api.helper.ChatHelper;
import de.caritas.cob.UserService.api.helper.UserHelper;
import de.caritas.cob.UserService.api.manager.consultingType.ConsultingTypeManager;
import de.caritas.cob.UserService.api.model.AgencyDTO;
import de.caritas.cob.UserService.api.model.ConsultantResponseDTO;
import de.caritas.cob.UserService.api.model.ConsultantSessionListResponseDTO;
import de.caritas.cob.UserService.api.model.ConsultantSessionResponseDTO;
import de.caritas.cob.UserService.api.model.MonitoringDTO;
import de.caritas.cob.UserService.api.model.SessionConsultantForUserDTO;
import de.caritas.cob.UserService.api.model.SessionDTO;
import de.caritas.cob.UserService.api.model.UserDTO;
import de.caritas.cob.UserService.api.model.UserDataResponseDTO;
import de.caritas.cob.UserService.api.model.UserSessionListResponseDTO;
import de.caritas.cob.UserService.api.model.UserSessionResponseDTO;
import de.caritas.cob.UserService.api.model.keycloak.KeycloakCreateUserResponseDTO;
import de.caritas.cob.UserService.api.model.keycloak.login.LoginResponseDTO;
import de.caritas.cob.UserService.api.repository.chat.Chat;
import de.caritas.cob.UserService.api.repository.consultant.Consultant;
import de.caritas.cob.UserService.api.repository.consultant.ConsultantRepository;
import de.caritas.cob.UserService.api.repository.session.ConsultingType;
import de.caritas.cob.UserService.api.repository.session.Session;
import de.caritas.cob.UserService.api.repository.session.SessionRepository;
import de.caritas.cob.UserService.api.repository.session.SessionStatus;
import de.caritas.cob.UserService.api.repository.user.User;
import de.caritas.cob.UserService.api.service.AskerImportService;
import de.caritas.cob.UserService.api.service.ChatService;
import de.caritas.cob.UserService.api.service.ConsultantAgencyService;
import de.caritas.cob.UserService.api.service.ConsultantImportService;
import de.caritas.cob.UserService.api.service.ConsultantService;
import de.caritas.cob.UserService.api.service.DecryptionService;
import de.caritas.cob.UserService.api.service.KeycloakService;
import de.caritas.cob.UserService.api.service.LogService;
import de.caritas.cob.UserService.api.service.MonitoringService;
import de.caritas.cob.UserService.api.service.RocketChatService;
import de.caritas.cob.UserService.api.service.SessionService;
import de.caritas.cob.UserService.api.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(secure = false)
@TestPropertySource(properties = "spring.profiles.active=testing")
public class UserControllerIT {

  private final String MESSAGE = "Lorem Ipsum";
  private final Long MESSAGE_DATE = 123456L;
  private final String VALID_ENQUIRY_MESSAGE_BODY = "{\"message\": \"" + MESSAGE + "\"}";
  private final String VALID_ABSENT_MESSAGE_BODY =
      "{\"absent\": true, \"message\": \"" + MESSAGE + "\"}";
  private final User USER = new User(USER_ID, "username", "name@domain.de", null);
  private final Consultant TEAM_CONSULTANT =
      new Consultant(CONSULTANT_ID, ROCKETCHAT_ID, "consultant", "first name", "last name",
          "consultant@cob.de", false, true, "", false, null, null, null);
  private final Consultant CONSULTANT = new Consultant(CONSULTANT_ID, ROCKETCHAT_ID, "consultant",
      "first name", "last name", "consultant@cob.de", false, false, "", false, null, null, null);
  private final Optional<Consultant> OPTIONAL_CONSULTANT = Optional.of(TEAM_CONSULTANT);
  private final String DUMMY_ROLE_A = "dummyRoleA";
  private final String DUMMY_ROLE_B = "dummyRoleB";
  private final Set<String> ROLES_INVALID =
      new HashSet<>(Arrays.asList(DUMMY_ROLE_A, DUMMY_ROLE_B));
  private final Set<String> ROLES_WITH_USER =
      new HashSet<>(Arrays.asList(DUMMY_ROLE_A, UserRole.USER.getValue(), DUMMY_ROLE_B));
  private final Set<String> ROLES_WITH_CONSULTANT =
      new HashSet<>(Arrays.asList(DUMMY_ROLE_A, UserRole.CONSULTANT.getValue(), DUMMY_ROLE_B));
  private final String VALID_USER_ROLE_RESULT = "{\"userRoles\": [\"" + DUMMY_ROLE_A + "\",\""
      + UserRole.USER.getValue() + "\",\"" + DUMMY_ROLE_B + "\"],\"grantedAuthorities\": [\""
      + Authority.USER_DEFAULT + "\"], \"inTeamAgency\":false}";
  private final String VALID_CONSULTANT_ROLE_RESULT =
      "{\"userRoles\": [\"" + DUMMY_ROLE_A + "\",\"" + UserRole.CONSULTANT.getValue() + "\",\""
          + DUMMY_ROLE_B + "\"], \"grantedAuthorities\": [ \"" + Authority.CONSULTANT_DEFAULT
          + "\" ], \"inTeamAgency\":true}";
  private final SessionDTO SESSION_DTO = new SessionDTO(SESSION_ID, AGENCY_ID, 0, 2, POSTCODE,
      RC_GROUP_ID, null, RC_USER_ID, MESSAGE_DATE, IS_NO_TEAM_SESSION, IS_MONITORING);
  private final AgencyDTO AGENCY_DTO =
      new AgencyDTO(AGENCY_ID, NAME, POSTCODE, CITY, DESCRIPTION, false, false,
          ConsultingType.SUCHT);
  private final SessionConsultantForUserDTO SESSION_CONSULTANT_DTO =
      new SessionConsultantForUserDTO(NAME, IS_ABSENT, ABSENCE_MESSAGE);
  private final UserSessionResponseDTO USER_SESSION_RESPONSE_DTO =
      new UserSessionResponseDTO(SESSION_DTO, AGENCY_DTO, SESSION_CONSULTANT_DTO);
  private final List<AgencyDTO> AGENCY_LIST = new ArrayList<>();
  @SuppressWarnings("serial")
  private final LinkedHashMap<String, Object> SESSION_DATA = new LinkedHashMap<String, Object>() {
    {
      put("age", "1");
      put("state", "4");
    }
  };
  private final UserDataResponseDTO CONSULTANT_USER_DATA_RESPONSE_DTO =
      new UserDataResponseDTO(USER_ID, "Beraterbiene", "Max", "Mustermann", "mail@muster.mann",
          true, false, "Bin weg", true, AGENCY_LIST, null, null, null);
  private final UserDataResponseDTO USER_USER_DATA_RESPONSE_DTO = new UserDataResponseDTO(USER_ID,
      NAME, null, null, null, false, false, null, false, null, null, null, SESSION_DATA);
  private final String VALID_NEW_MESSAGE_REQUEST_BODY = "{\"rcGroupId\": \"" + RC_GROUP_ID + "\"}";
  private final String PATH_PUT_SESSIONS_MONITORING = "/users/sessions/monitoring/" + SESSION_ID;
  private final String PATH_GET_MONITORING = "/users/sessions/" + SESSION_ID + "/monitoring";
  private final String VALID_SESSION_MONITORING_REQUEST_BODY = "{\"addictiveDrugs\": { \"drugs\":"
      + "{\"others\": false} }, \"intervention\": { \"information\": false } }";
  private final String ERROR = "error";
  private final Session SESSION = new Session(SESSION_ID, USER, TEAM_CONSULTANT,
      ConsultingType.SUCHT, POSTCODE, AGENCY_ID, SessionStatus.IN_PROGRESS, new Date(), RC_GROUP_ID,
      null, IS_NO_TEAM_SESSION, IS_MONITORING);
  private final Session SESSION_WITHOUT_CONSULTANT =
      new Session(SESSION_ID, USER, null, ConsultingType.SUCHT, POSTCODE, AGENCY_ID,
          SessionStatus.NEW, new Date(), RC_GROUP_ID, null, IS_NO_TEAM_SESSION, IS_MONITORING);
  private final Optional<Session> OPTIONAL_SESSION = Optional.of(SESSION);
  private final Optional<Session> OPTIONAL_SESSION_WITHOUT_CONSULTANT =
      Optional.of(SESSION_WITHOUT_CONSULTANT);
  private final Session TEAM_SESSION =
      new Session(SESSION_ID, USER, TEAM_CONSULTANT, ConsultingType.SUCHT, POSTCODE, AGENCY_ID,
          SessionStatus.IN_PROGRESS, new Date(), RC_GROUP_ID, null, IS_TEAM_SESSION, IS_MONITORING);
  private final Session TEAM_SESSION_WITHOUT_GROUP_ID =
      new Session(SESSION_ID, USER, TEAM_CONSULTANT, ConsultingType.SUCHT, POSTCODE, AGENCY_ID,
          SessionStatus.IN_PROGRESS, new Date(), null, null, IS_TEAM_SESSION, IS_MONITORING);
  private final Optional<Session> OPTIONAL_TEAM_SESSION = Optional.of(TEAM_SESSION);
  private final Optional<Session> OPTIONAL_TEAM_SESSION_WITHOUT_GROUP_ID =
      Optional.of(TEAM_SESSION_WITHOUT_GROUP_ID);
  private final String FIRST_NAME = "Max";
  private final String LAST_NAME = "Mustermann";
  private final ConsultantResponseDTO CONSULTANT_RESPONSE_DTO =
      new ConsultantResponseDTO(CONSULTANT_ID, FIRST_NAME, LAST_NAME);
  private final List<ConsultantResponseDTO> CONSULTANT_RESPONSE_DTO_LIST =
      Arrays.asList(CONSULTANT_RESPONSE_DTO);
  private final String VALID_CONSULTANT_RESPONSE_DTO_RESULT =
      "[{\"consultantId\": \"" + CONSULTANT_ID + "\", \"firstName\": \"" + FIRST_NAME
          + "\", \"lastName\": \"" + LAST_NAME + "\"}]";
  private final String VALID_PASSWORT_REQUEST_BODY =
      "{ \"oldPassword\": \"0lDpw!\", " + "\"newPassword\": \"n3wPw!\" }";
  private final Optional<ResponseEntity<LoginResponseDTO>> LOGIN_RESPONSE_ENTITY_BAD_REQUEST =
      Optional.of(new ResponseEntity<LoginResponseDTO>(HttpStatus.BAD_REQUEST));
  private final String ACCESS_TOKEN = "askdasd09SUIasdmw9-sdfk94r";
  private final String REFRESH_TOKEN = "askdasd09SUIasdmw9-sdfk94r";
  private final LoginResponseDTO LOGIN_RESPONSE_DTO =
      new LoginResponseDTO(ACCESS_TOKEN, 0, 0, REFRESH_TOKEN, null, null, null);
  private final Optional<ResponseEntity<LoginResponseDTO>> LOGIN_RESPONSE_ENTITY_OK =
      Optional.of(new ResponseEntity<LoginResponseDTO>(LOGIN_RESPONSE_DTO, HttpStatus.OK));
  private final Set<String> AUTHORITIES_ASSIGN_SESSION_AND_ENQUIRY = new HashSet<>(Arrays
      .asList(Authority.ASSIGN_CONSULTANT_TO_ENQUIRY, Authority.ASSIGN_CONSULTANT_TO_SESSION));
  private final Set<String> AUTHORITY_ASSIGN_SESSION =
      new HashSet<>(Arrays.asList(Authority.ASSIGN_CONSULTANT_TO_SESSION));
  private final Set<String> AUTHORITY_ASSIGN_ENQUIRY =
      new HashSet<>(Arrays.asList(Authority.ASSIGN_CONSULTANT_TO_ENQUIRY));
  private final MonitoringDTO MONITORING_DTO = new MonitoringDTO();
  private final String VALID_MONITORING_RESPONSE_JSON =
      "{\"addictiveDrugs\": { \"drugs\": {" + "\"others\": false } } }";
  KeycloakCreateUserResponseDTO KEYCLOAK_CREATE_USER_RESPONSE_DTO =
      new KeycloakCreateUserResponseDTO(USER_ID);

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;
  @MockBean
  private SessionService sessionService;
  @MockBean
  private SessionRepository sessionRepository;
  @MockBean
  private AuthenticatedUser authenticatedUser;
  @MockBean
  private CreateEnquiryMessageFacade createEnquiryMessageFacade;
  @MockBean
  private GetUserDataFacade getUserDataFacade;
  @MockBean
  private LogService logService;
  @MockBean
  private ConsultantService consultantService;
  @MockBean
  private ConsultantImportService consultantImportService;
  @MockBean
  private EmailNotificationFacade emailNotificationFacade;
  @MockBean
  private MonitoringService monitoringService;
  @MockBean
  private AskerImportService askerImportService;
  @MockBean
  private GetSessionListFacade getSessionListFacade;
  @MockBean
  private ConsultantAgencyService consultantAgencyService;
  @MockBean
  private AssignSessionFacade assignSessionFacade;
  @MockBean
  private KeycloakService keycloakService;
  @MockBean
  private DecryptionService encryptionService;
  @MockBean
  private AuthenticatedUserHelper authenticatedUserHelper;
  @MockBean
  private ConsultingTypeManager consultingTypeManager;
  @MockBean
  private UserHelper userHelper;
  @MockBean
  private ChatService chatService;
  @MockBean
  private StartChatFacade startChatFacade;
  @MockBean
  private GetChatFacade getChatFacade;
  @MockBean
  private JoinAndLeaveChatFacade joinAndLeaveChatFacade;
  @MockBean
  private CreateChatFacade createChatFacade;
  @MockBean
  private RocketChatService rocketChatService;
  @MockBean
  private ChatHelper chatHelper;
  @MockBean
  private StopChatFacade stopChatFacade;
  @MockBean
  private ConsultantRepository consultantRepository;
  @MockBean
  private GetChatMembersFacade getChatMembersFacade;
  @MockBean
  private CreateUserFacade createUserFacade;
  @MockBean
  private DeleteUserFacade deleteUserFacade;

  @Mock
  private Chat chat;

  @Before
  public void setUp() {
    HashMap<String, Object> drugsMap = new HashMap<String, Object>();
    drugsMap.put("others", false);
    HashMap<String, Object> addictiveDrugsMap = new HashMap<String, Object>();
    addictiveDrugsMap.put("drugs", drugsMap);
    MONITORING_DTO.addProperties("addictiveDrugs", addictiveDrugsMap);
  }

  @Test
  public void registerUser_Should_ReturnBadRequest_WhenProvidedWithInvalidRequestBody()
      throws Exception {

    mvc.perform(post(PATH_REGISTER_USER).content(INVALID_USER_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void registerUser_Should_ReturnBadRequest_WhenProvidedWithConsultingTypeWithMandatoryFieldsAndInvalidAge()
      throws Exception {

    when(consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_U25))
        .thenReturn(CONSULTING_TYPE_SETTINGS_U25);

    mvc.perform(post(PATH_REGISTER_USER).content(INVALID_U25_USER_REQUEST_BODY_AGE)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void registerUser_Should_ReturnBadRequest_WhenProvidedWithConsultingTypeWithMandatoryFieldsAndInvalidState()
      throws Exception {

    when(consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_U25))
        .thenReturn(CONSULTING_TYPE_SETTINGS_WITH_MANDATORY_FIELDS);

    mvc.perform(post(PATH_REGISTER_USER).content(INVALID_U25_USER_REQUEST_BODY_STATE)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void registerUser_Should_ReturnBadRequest_WhenProvidedUsernameIsTooShort()
      throws Exception {

    when(consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_SUCHT))
        .thenReturn(CONSULTING_TYPE_SETTINGS_WITHOUT_MANDATORY_FIELDS);

    mvc.perform(post(PATH_REGISTER_USER).content(USER_REQUEST_BODY_WITH_USERNAME_TOO_SHORT)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void registerUser_Should_ReturnBadRequest_WhenProvidedUsernameIsTooLong()
      throws Exception {

    when(consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_SUCHT))
        .thenReturn(CONSULTING_TYPE_SETTINGS_WITHOUT_MANDATORY_FIELDS);

    mvc.perform(post(PATH_REGISTER_USER).content(USER_REQUEST_BODY_WITH_USERNAME_TOO_LONG)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void registerUser_Should_ReturnCreated_WhenProvidedWithValidRequestBodyAndKeycloakResponseIsSuccessfull()
      throws Exception {

    KeycloakCreateUserResponseDTO response = new KeycloakCreateUserResponseDTO(USER_ID);
    when(createUserFacade.createUserAndInitializeAccount(Mockito.any())).thenReturn(response);
    when(consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_SUCHT))
        .thenReturn(CONSULTING_TYPE_SETTINGS_WITHOUT_MANDATORY_FIELDS);

    mvc.perform(post(PATH_REGISTER_USER).content(VALID_USER_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void registerUser_Should_ReturnCreated_WhenProvidedWithValidU25RequestBodyAndKeycloakResponseIsSuccessfull()
      throws Exception {

    KeycloakCreateUserResponseDTO response = new KeycloakCreateUserResponseDTO(USER_ID);
    when(createUserFacade.createUserAndInitializeAccount(Mockito.any())).thenReturn(response);
    when(consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_U25))
        .thenReturn(CONSULTING_TYPE_SETTINGS_WITH_MANDATORY_FIELDS);

    mvc.perform(post(PATH_REGISTER_USER).content(VALID_U25_USER_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void registerUser_Should_ReturnConflict_WhenProvidedWithValidRequestBodyAndKeycloakResponseIsConflict()
      throws Exception {

    KeycloakCreateUserResponseDTO response = new KeycloakCreateUserResponseDTO(HttpStatus.CONFLICT);
    when(createUserFacade.createUserAndInitializeAccount(Mockito.any())).thenReturn(response);
    when(consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_SUCHT))
        .thenReturn(CONSULTING_TYPE_SETTINGS_WITHOUT_MANDATORY_FIELDS);

    mvc.perform(post(PATH_REGISTER_USER).content(VALID_USER_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isConflict());
  }

  @Test
  public void acceptEnquiry_Should_ReturnInternalServerError_WhenNoConsultantInDbFound()
      throws Exception {

    when(sessionService.getSession(SESSION_ID)).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(Optional.empty());

    mvc.perform(
        put(PATH_ACCEPT_ENQUIRY + SESSION_ID).header(RC_USER_ID_HEADER_PARAMETER_NAME, RC_USER_ID)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    verify(authenticatedUser, atLeastOnce()).getUserId();
    verify(logService, atLeastOnce()).logInternalServerError(Mockito.anyString());
  }

  @Test
  public void acceptEnquiry_Should_ReturnInternalServerError_WhenSessionNotFoundInDb()
      throws Exception {

    when(sessionService.getSession(SESSION_ID)).thenReturn(Optional.empty());
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(OPTIONAL_CONSULTANT);

    mvc.perform(
        put(PATH_ACCEPT_ENQUIRY + SESSION_ID).header(RC_USER_ID_HEADER_PARAMETER_NAME, RC_USER_ID)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    verify(logService, atLeastOnce()).logInternalServerError(Mockito.anyString());
  }

  @Test
  public void acceptEnquiry_Should_ReturnInternalServerError_WhenSessionHasNoRocketChatGroupId()
      throws Exception {

    when(sessionService.getSession(SESSION_ID)).thenReturn(OPTIONAL_TEAM_SESSION_WITHOUT_GROUP_ID);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(OPTIONAL_CONSULTANT);

    mvc.perform(
        put(PATH_ACCEPT_ENQUIRY + SESSION_ID).header(RC_USER_ID_HEADER_PARAMETER_NAME, RC_USER_ID)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    verify(logService, atLeastOnce()).logInternalServerError(Mockito.anyString());
  }

  @Test
  public void acceptEnquiry_Should_ReturnSuccess_WhenAcceptEnquiryIsSuccessfull() throws Exception {

    when(sessionService.getSession(SESSION_ID)).thenReturn(OPTIONAL_TEAM_SESSION);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(Optional.of(TEAM_CONSULTANT));
    when(assignSessionFacade.assignSession(TEAM_SESSION, TEAM_CONSULTANT, true))
        .thenReturn(HttpStatus.OK);

    mvc.perform(
        put(PATH_ACCEPT_ENQUIRY + SESSION_ID).header(RC_USER_ID_HEADER_PARAMETER_NAME, RC_USER_ID)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  public void acceptEnquiry_Should_ReturnConflict_WhenEnquiryIsAlreadyAssigned() throws Exception {

    when(sessionService.getSession(SESSION_ID)).thenReturn(OPTIONAL_TEAM_SESSION);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(Optional.of(TEAM_CONSULTANT));
    when(assignSessionFacade.assignSession(TEAM_SESSION, TEAM_CONSULTANT, true))
        .thenReturn(HttpStatus.CONFLICT);

    mvc.perform(
        put(PATH_ACCEPT_ENQUIRY + SESSION_ID).header(RC_USER_ID_HEADER_PARAMETER_NAME, RC_USER_ID)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.CONFLICT.value()));
  }

  @Test
  public void acceptEnquiry_Should_ReturnInternalServerError_WhenAuthenticatedUserIsNotPresentInApplicationDb()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.empty());

    mvc.perform(post(PATH_CREATE_ENQUIRY_MESSAGE).header(RC_TOKEN_HEADER_PARAMETER_NAME, "xxx")
        .header(RC_USER_ID_HEADER_PARAMETER_NAME, "xxx").content(VALID_ENQUIRY_MESSAGE_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

  }

  @Test
  public void createEnquiryMessage_Should_ReturnConflict_WhenMessageIsAlreadyCreated()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.of(USER));
    when(createEnquiryMessageFacade.createEnquiryMessage(USER, SESSION_ID, MESSAGE, RC_TOKEN,
        RC_USER_ID)).thenReturn(HttpStatus.CONFLICT);

    mvc.perform(post(PATH_CREATE_ENQUIRY_MESSAGE).header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN)
        .header(RC_USER_ID_HEADER_PARAMETER_NAME, RC_USER_ID).content(VALID_ENQUIRY_MESSAGE_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.CONFLICT.value()));
  }



  @Test
  public void createEnquiryMessage_Should_ReturnCreated_WhenMessageWasCreated() throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.of(USER));
    when(createEnquiryMessageFacade.createEnquiryMessage(USER, SESSION_ID, MESSAGE, RC_TOKEN,
        RC_USER_ID)).thenReturn(HttpStatus.CREATED);

    mvc.perform(post(PATH_CREATE_ENQUIRY_MESSAGE).header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN)
        .header(RC_USER_ID_HEADER_PARAMETER_NAME, RC_USER_ID).content(VALID_ENQUIRY_MESSAGE_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.CREATED.value()));

  }


  @Test
  public void createEnquiryMessage_Should_ReturnInternalServerError_WhenAuthenticatedUserIsNotPresentInApplicationDb()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.empty());

    mvc.perform(post(PATH_CREATE_ENQUIRY_MESSAGE).header(RC_TOKEN_HEADER_PARAMETER_NAME, "xxx")
        .header(RC_USER_ID_HEADER_PARAMETER_NAME, "xxx").content(VALID_ENQUIRY_MESSAGE_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

  }

  /**
   * Method: getSessionsForAuthenticatedUser (role: user)
   * 
   */

  @Test
  public void getSessionsForAuthenticatedUser_Should_ReturnUserSessionsAndOk_WhenAuthorized()
      throws Exception {

    List<UserSessionResponseDTO> sessions = new ArrayList<UserSessionResponseDTO>();
    sessions.add(USER_SESSION_RESPONSE_DTO);
    UserSessionListResponseDTO response = new UserSessionListResponseDTO(sessions);
    String sessionsJson = convertObjectToJson(response);

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.of(USER));

    when(getSessionListFacade.getSessionsForAuthenticatedUser(USER.getUserId(), USER.getRcUserId(),
        RC_TOKEN)).thenReturn(response);

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_USER)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().json(sessionsJson));

    verify(authenticatedUser, atLeastOnce()).getUserId();
    verify(userService, atLeastOnce()).getUser(USER_ID);

  }

  @Test
  public void getSessionsForAuthenticatedUser_Should_ReturnInternalServerError_WhenAuthorizedButUserNotFound()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.empty());

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_USER)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());

    verify(getSessionListFacade, times(0)).getSessionsForAuthenticatedUser(Mockito.any(),
        Mockito.any(), Mockito.any());

  }

  @Test
  public void getSessionsForAuthenticatedUser_Should_ReturnNoContent_WhenAuthorizedAndNoOpenSessionsAvailableAndSessionListIsNull()
      throws Exception {
    List<UserSessionResponseDTO> session = null;
    UserSessionListResponseDTO response = new UserSessionListResponseDTO(session);

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.of(USER));

    when(getSessionListFacade.getSessionsForAuthenticatedUser(USER.getUserId(), USER.getRcUserId(),
        RC_TOKEN)).thenReturn(response);

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_USER)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

    verify(authenticatedUser, atLeastOnce()).getUserId();
  }

  @Test
  public void getSessionsForAuthenticatedUser_Should_ReturnNoContent_WhenAuthorizedAndNoOpenSessionsAvailableAndSessionListIsEmpty()
      throws Exception {
    List<UserSessionResponseDTO> session = new ArrayList<UserSessionResponseDTO>();
    UserSessionListResponseDTO response = new UserSessionListResponseDTO(session);

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.of(USER));

    when(getSessionListFacade.getSessionsForAuthenticatedUser(USER.getUserId(), USER.getRcUserId(),
        RC_TOKEN)).thenReturn(response);

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_USER)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

    verify(authenticatedUser, atLeastOnce()).getUserId();
  }


  @Test
  public void getSessionsForAuthenticatedUser_Should_ReturnBadRequest_WhenHeaderParamIsMissing()
      throws Exception {

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_USER)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateAbsence_Should_ReturnOk_WhenSaved() throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(Optional.of(TEAM_CONSULTANT));

    mvc.perform(put(PATH_PUT_CONSULTANT_ABSENT).content(VALID_ABSENT_MESSAGE_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void updateAbsence_Should_ReturnInternalServerError_WhenAuthenticatedUserIsNotPresentInApplicationDb()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(Optional.empty());

    mvc.perform(put(PATH_PUT_CONSULTANT_ABSENT).content(VALID_ABSENT_MESSAGE_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  private String convertObjectToJson(Object object) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

  /**
   * Method: getSessionsForAuthenticatedConsultant (role: consultant)
   * 
   */

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenStatusParamIsMissing()
      throws Exception {
    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_STATUS)
        .header(RC_USER_ID_HEADER_PARAMETER_NAME, RC_USER_ID)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verifyNoMoreInteractions(authenticatedUser, sessionService);
  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenHeaderParamIsMissing()
      throws Exception {
    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verifyNoMoreInteractions(authenticatedUser, sessionService);
  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnInternalServerError_WhenNoConsultantInDbFound()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(Optional.empty());

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnSucess_WhenAuthorizedAndSessionAvailable()
      throws Exception {

    List<ConsultantSessionResponseDTO> session = new ArrayList<ConsultantSessionResponseDTO>();
    session.add(new ConsultantSessionResponseDTO());
    ConsultantSessionListResponseDTO consultantSessionListResponseDTO =
        new ConsultantSessionListResponseDTO(session, OFFSET_0, COUNT_10, 1);

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(Optional.of(TEAM_CONSULTANT));

    when(getSessionListFacade.getSessionsForAuthenticatedConsultant(TEAM_CONSULTANT,
        SESSION_STATUS_NEW, RC_TOKEN, OFFSET_0, COUNT_10, SESSION_FILTER_ALL))
            .thenReturn(consultantSessionListResponseDTO);

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnNoContent_WhenAuthorizedAndNoSessionsAvailable()
      throws Exception {

    ConsultantSessionListResponseDTO consultantSessionListResponseDTO =
        new ConsultantSessionListResponseDTO(null, OFFSET_0, COUNT_10, 0);

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(Optional.of(TEAM_CONSULTANT));

    when(getSessionListFacade.getSessionsForAuthenticatedConsultant(TEAM_CONSULTANT,
        SESSION_STATUS_NEW, RC_TOKEN, OFFSET_0, COUNT_10, SESSION_FILTER_ALL))
            .thenReturn(consultantSessionListResponseDTO);

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnNoContent_WhenAuthorizedAndSessionListIsEmpty()
      throws Exception {

    List<ConsultantSessionResponseDTO> session = new ArrayList<ConsultantSessionResponseDTO>();
    ConsultantSessionListResponseDTO consultantSessionListResponseDTO =
        new ConsultantSessionListResponseDTO(session, OFFSET_0, COUNT_10, 0);

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(Optional.of(TEAM_CONSULTANT));

    when(getSessionListFacade.getSessionsForAuthenticatedConsultant(TEAM_CONSULTANT,
        SESSION_STATUS_NEW, RC_TOKEN, OFFSET_0, COUNT_10, SESSION_FILTER_ALL))
            .thenReturn(consultantSessionListResponseDTO);

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenParamOffestIsMissing()
      throws Exception {

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_OFFSET)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenParamOffestHasANegativeValue()
      throws Exception {

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_NEGATIVE_OFFSET)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenParamCountIsMissing()
      throws Exception {

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_COUNT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenParamCountHasANegativeValue()
      throws Exception {

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_NEGATIVE_COUNT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }

  @Test
  public void getSessionsForAuthenticatedConsultant_Should_ReturnNotContent_WhenFilterParamIsInvalid()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(OPTIONAL_CONSULTANT);

    mvc.perform(get(PATH_GET_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_INVALID_FILTER)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  /**
   * Method: getUserData (role: consultant / user)
   * 
   */

  @Test
  public void getUserData_ForUser_Should_ReturnOkAndValidContent() throws Exception {

    when(authenticatedUser.getRoles()).thenReturn(ROLES_WITH_USER);
    when(authenticatedUser.getGrantedAuthorities())
        .thenReturn(Authority.getAuthoritiesByUserRole(UserRole.USER).stream()
            .map(authority -> authority.toString()).collect(Collectors.toSet()));
    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.of(USER));
    when(getUserDataFacade.getUserData(Mockito.any())).thenReturn(USER_USER_DATA_RESPONSE_DTO);

    mvc.perform(get(PATH_USER_DATA).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().json(VALID_USER_ROLE_RESULT));
  }

  @Test
  public void getUserData_ForUser_Should_ReturnInternalServerError_WhenAuthenticatedUserIsNotPresentInApplicationDb()
      throws Exception {

    when(authenticatedUser.getRoles()).thenReturn(ROLES_WITH_USER);
    when(authenticatedUser.getGrantedAuthorities())
        .thenReturn(Authority.getAuthoritiesByUserRole(UserRole.USER).stream()
            .map(authority -> authority.toString()).collect(Collectors.toSet()));
    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(userService.getUser(USER_ID)).thenReturn(Optional.of(USER));
    when(getUserDataFacade.getUserData(Mockito.any())).thenReturn(null);

    mvc.perform(get(PATH_USER_DATA).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void getUserData_ForUser_Should_ReturnInternalServerError_WhenUserDataFacadeReturnsEmptyDTO()
      throws Exception {

    when(authenticatedUser.getRoles()).thenReturn(ROLES_INVALID);

    mvc.perform(get(PATH_USER_DATA).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void getUserData_ForConsultant_Should_ReturnOkAndValidContent() throws Exception {

    when(authenticatedUser.getRoles()).thenReturn(ROLES_WITH_CONSULTANT);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(Optional.of(TEAM_CONSULTANT));
    when(getUserDataFacade.getConsultantData(TEAM_CONSULTANT))
        .thenReturn(CONSULTANT_USER_DATA_RESPONSE_DTO);
    when(authenticatedUser.getGrantedAuthorities())
        .thenReturn(Authority.getAuthoritiesByUserRole(UserRole.CONSULTANT).stream()
            .map(authority -> authority.toString()).collect(Collectors.toSet()));

    mvc.perform(get(PATH_USER_DATA).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().json(VALID_CONSULTANT_ROLE_RESULT));
  }

  @Test
  public void getUserData_Should_ReturnInternalServerError_WhenAuthenticatedUserHasNoValidRole()
      throws Exception {

    when(authenticatedUser.getRoles()).thenReturn(ROLES_INVALID);

    mvc.perform(get(PATH_USER_DATA).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void getUserData_ForConsultant_Should_ReturnInternalServerError_WhenAuthenticatedUserIsNotPresentInApplicationDb()
      throws Exception {

    when(authenticatedUser.getRoles()).thenReturn(ROLES_WITH_CONSULTANT);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(Optional.empty());

    mvc.perform(get(PATH_USER_DATA).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  /**
   * Method: getTeamSessionsForAuthenticatedConsultant (role: consultant)
   * 
   */

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenHeaderParamIsMissing()
      throws Exception {

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnInternalServerError_WhenNoConsultantInDbFound()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(CONSULTANT_ID)).thenReturn(Optional.empty());

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnForbidden_WhenConsultantIsNoTeamConsultant()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(Optional.of(CONSULTANT));

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());

    verify(logService, atLeastOnce()).logForbidden(Mockito.anyString());
  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnNoContent_WhenAuthorizedAndNoSessionsAvailable()
      throws Exception {

    List<ConsultantSessionResponseDTO> session = null;
    ConsultantSessionListResponseDTO consultantSessionListResponseDTO =
        new ConsultantSessionListResponseDTO(session, OFFSET_0, COUNT_10, 0);

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(Optional.of(TEAM_CONSULTANT));
    when(getSessionListFacade.getTeamSessionsForAuthenticatedConsultant(TEAM_CONSULTANT, RC_TOKEN,
        OFFSET_0, COUNT_10, SESSION_FILTER_ALL)).thenReturn(consultantSessionListResponseDTO);

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnSucess_WhenAuthorizedAndSessionsAvailable()
      throws Exception {

    List<ConsultantSessionResponseDTO> session = new ArrayList<ConsultantSessionResponseDTO>();
    session.add(new ConsultantSessionResponseDTO());
    ConsultantSessionListResponseDTO consultantSessionListResponseDTO =
        new ConsultantSessionListResponseDTO(session, OFFSET_0, COUNT_10, 0);


    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(Optional.of(TEAM_CONSULTANT));

    when(getSessionListFacade.getTeamSessionsForAuthenticatedConsultant(TEAM_CONSULTANT, RC_TOKEN,
        OFFSET_0, COUNT_10, SESSION_FILTER_ALL)).thenReturn(consultantSessionListResponseDTO);

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenParamOffestIsMissing()
      throws Exception {

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_OFFSET)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenParamOffestHasANegativeValue()
      throws Exception {

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_NEGATIVE_OFFSET)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenParamCountIsMissing()
      throws Exception {

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITHOUT_COUNT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnBadRequest_WhenParamCountHasANegativeValue()
      throws Exception {

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_NEGATIVE_COUNT)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

  }

  @Test
  public void getTeamSessionsForAuthenticatedConsultant_Should_ReturnNotContent_WhenFilterParamIsInvalid()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(OPTIONAL_CONSULTANT);

    mvc.perform(get(PATH_GET_TEAM_SESSIONS_FOR_AUTHENTICATED_CONSULTANT_WITH_INVALID_FILTER)
        .header(RC_TOKEN_HEADER_PARAMETER_NAME, RC_TOKEN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  /**
   * 
   * sendNewMessageNotification()
   * 
   */


  @Test
  public void sendNewMessageNotification_Should_CallEmailNotificationFacadeAndReturn2xxSuccessful_WhenCalled()
      throws Exception {

    mvc.perform(post(PATH_SEND_NEW_MESSAGE_NOTIFICATION).content(VALID_NEW_MESSAGE_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());

    verify(emailNotificationFacade, atLeastOnce()).sendNewMessageNotification(RC_GROUP_ID,
        authenticatedUser.getRoles(), authenticatedUser.getUserId());
  }

  /**
   * getMonitoring()
   * 
   */

  @Test
  public void getMonitoring_Should_ReturnBadRequestAndLogError_WhenSessionNotFound()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(Optional.empty());

    mvc.perform(get(PATH_GET_MONITORING).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    verify(logService, times(1)).logBadRequest(Mockito.anyString());
  }

  @Test
  public void getMonitoring_Should_ReturnInternalServerError_WhenSessionServiceThrowsException()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenThrow(new ServiceException(ERROR));

    mvc.perform(get(PATH_GET_MONITORING).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void getMonitoring_Should_ReturnBadRequestAndLogError_WhenUserHasNoPermissionToAccessSession()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUserHelper.hasPermissionForSession(OPTIONAL_SESSION.get())).thenReturn(false);

    mvc.perform(get(PATH_GET_MONITORING).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    verify(logService, times(1)).logBadRequest(Mockito.anyString());
  }

  @Test
  public void getMonitoring_Should_ReturnOKAndMonitoring() throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUserHelper.hasPermissionForSession(OPTIONAL_SESSION.get())).thenReturn(true);
    when(monitoringService.getMonitoring(OPTIONAL_SESSION.get())).thenReturn(MONITORING_DTO);

    mvc.perform(get(PATH_GET_MONITORING).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.OK.value()))
        .andExpect(content().json(VALID_MONITORING_RESPONSE_JSON));
  }

  @Test
  public void getMonitoring_Should_ReturnNoContent_WhenNoMonitoringFoundForSession()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUserHelper.hasPermissionForSession(OPTIONAL_SESSION.get())).thenReturn(true);
    when(monitoringService.getMonitoring(OPTIONAL_SESSION.get())).thenReturn(null);

    mvc.perform(get(PATH_GET_MONITORING).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
  }

  /**
   * updateMonitoring()
   * 
   */

  @Test
  public void updateMonitoring_Should_ReturnInternalServerError_WhenSessionServiceThrowsException()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenThrow(new ServiceException(ERROR));

    mvc.perform(put(PATH_PUT_SESSIONS_MONITORING).content(VALID_SESSION_MONITORING_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void updateMonitoring_Should_ReturnInternalServerError_WhenMonitoringServiceThrowsException()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUserHelper.hasPermissionForSession(OPTIONAL_SESSION.get())).thenReturn(true);
    doThrow(new ServiceException(ERROR)).when(monitoringService).updateMonitoring(Mockito.any(),
        Mockito.any());

    mvc.perform(put(PATH_PUT_SESSIONS_MONITORING).content(VALID_SESSION_MONITORING_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void updateMonitoring_Should_ReturnOK_WhenMonitoringWasUpdatedForSingleSession()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUserHelper.hasPermissionForSession(OPTIONAL_SESSION.get())).thenReturn(true);

    mvc.perform(put(PATH_PUT_SESSIONS_MONITORING).content(VALID_SESSION_MONITORING_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  public void updateMonitoring_Should_ReturnOK_WhenMonitoringWasUpdatedForTeamSession()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_TEAM_SESSION);
    when(authenticatedUserHelper.hasPermissionForSession(OPTIONAL_TEAM_SESSION.get()))
        .thenReturn(true);

    mvc.perform(put(PATH_PUT_SESSIONS_MONITORING).content(VALID_SESSION_MONITORING_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  public void updateMonitoring_Should_ReturnUnauthorized_WhenConsultantIsNotAssignedToSingleSession()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID + "notAssignedToAgency");

    mvc.perform(put(PATH_PUT_SESSIONS_MONITORING).content(VALID_SESSION_MONITORING_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));

    verify(logService, atLeastOnce()).logUnauthorized(Mockito.anyString());
  }

  @Test
  public void updateMonitoring_Should_ReturnUnauthorized_WhenConsultantIsNotAssignedToAgencyOfTeamSession()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_TEAM_SESSION);
    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantAgencyService.isConsultantInAgency(CONSULTANT_ID, AGENCY_ID)).thenReturn(false);

    mvc.perform(put(PATH_PUT_SESSIONS_MONITORING).content(VALID_SESSION_MONITORING_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));

    verify(logService, atLeastOnce()).logUnauthorized(Mockito.anyString());
  }

  @Test
  public void updateMonitoring_Should_ReturnBadRequest_WhenSessionDoesNotExist() throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenReturn(Optional.empty());

    mvc.perform(put(PATH_PUT_SESSIONS_MONITORING).content(VALID_SESSION_MONITORING_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    verify(logService, atLeastOnce()).logBadRequest(Mockito.anyString());
  }

  /**
   * Method: getConsultants (authority: VIEW_AGENCY_CONSULTANTS)
   * 
   */

  @Test
  public void getConsultants_Should_ReturnBadRequest_WhenQueryParamIsMissing() throws Exception {

    mvc.perform(get(PATH_GET_CONSULTANTS_FOR_AGENCY_WITHOUT_PARAM)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getConsultants_Should_ReturnNoContent_WhenNoConsultantInDbFound() throws Exception {

    mvc.perform(get(PATH_GET_CONSULTANTS_FOR_AGENCY).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  @Test
  public void getConsultants_Should_ReturnInternalServerError_WhenConsultantAgencyServiceThrowsException()
      throws Exception {

    when(consultantAgencyService.getConsultantsOfAgency(Mockito.anyLong()))
        .thenThrow(new ServiceException(ERROR));

    mvc.perform(get(PATH_GET_CONSULTANTS_FOR_AGENCY).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void getConsultants_Should_ReturnOkAndValidContent_WhenConsultantAgencyServiceReturnsListWithEntries()
      throws Exception {

    when(consultantAgencyService.getConsultantsOfAgency(Mockito.anyLong()))
        .thenReturn(CONSULTANT_RESPONSE_DTO_LIST);

    mvc.perform(get(PATH_GET_CONSULTANTS_FOR_AGENCY).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().json(VALID_CONSULTANT_RESPONSE_DTO_RESULT));
  }

  /**
   * Method: assignSession (role: consultant)
   * 
   */

  @Test
  public void assignSession_Should_ReturnBadRequest_WhenQueryParamsAreInvalid() throws Exception {

    mvc.perform(put(PATH_PUT_ASSIGN_SESSION_INVALID_PARAMS).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
  }

  @Test
  public void assignSession_Should_ReturnInternalServerErrorAndLogError_WhenSessionServiceThrowsException()
      throws Exception {

    when(sessionService.getSession(Mockito.anyLong())).thenThrow(new ServiceException(ERROR));

    mvc.perform(put(PATH_PUT_ASSIGN_SESSION).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    verify(logService, atLeastOnce()).logInternalServerError(Mockito.anyString());
  }

  @Test
  public void assignSession_Should_ReturnHttpStatusOfAssignSessionFacade() throws Exception {

    when(consultantService.getConsultant(Mockito.any())).thenReturn(OPTIONAL_CONSULTANT);
    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUser.getGrantedAuthorities())
        .thenReturn(AUTHORITIES_ASSIGN_SESSION_AND_ENQUIRY);
    when(
        assignSessionFacade.assignSession(OPTIONAL_SESSION.get(), OPTIONAL_CONSULTANT.get(), false))
            .thenReturn(HttpStatus.I_AM_A_TEAPOT);

    mvc.perform(put(PATH_PUT_ASSIGN_SESSION).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.I_AM_A_TEAPOT.value()));
  }

  @Test
  public void assignSession_Should_ReturnInternalServerErrorAndLogError_WhenConsultantIsNotFoundInDb()
      throws Exception {

    when(consultantService.getConsultant(Mockito.any())).thenReturn(OPTIONAL_CONSULTANT)
        .thenReturn(Optional.empty());

    mvc.perform(put(PATH_PUT_ASSIGN_SESSION).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    verify(logService, atLeastOnce()).logInternalServerError(Mockito.anyString());
  }

  @Test
  public void assignSession_Should_ReturnInternalServerErrorAndLogError_WhenSessionIsNotFoundInDb()
      throws Exception {

    when(consultantService.getConsultant(Mockito.any())).thenReturn(OPTIONAL_CONSULTANT);
    when(sessionService.getSession(Mockito.anyLong())).thenReturn(Optional.empty());

    mvc.perform(put(PATH_PUT_ASSIGN_SESSION).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    verify(logService, atLeastOnce()).logInternalServerError(Mockito.anyString());
  }

  @Test
  public void assignSession_Should_ReturnForbiddenAndLogError_WhenCallerDoesNotHaveTheRightToAssignSessions()
      throws Exception {

    when(consultantService.getConsultant(Mockito.any())).thenReturn(OPTIONAL_CONSULTANT);
    when(sessionService.getSession(Mockito.anyLong())).thenReturn(OPTIONAL_SESSION);
    when(authenticatedUser.getGrantedAuthorities()).thenReturn(AUTHORITY_ASSIGN_ENQUIRY);

    mvc.perform(put(PATH_PUT_ASSIGN_SESSION).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    verify(logService, atLeastOnce()).logForbidden(Mockito.anyString());
  }

  @Test
  public void assignSession_Should_ReturnForbiddenAndLogError_WhenCallerDoesNotHaveTheRightToAssignEnquiries()
      throws Exception {

    when(consultantService.getConsultant(Mockito.any())).thenReturn(OPTIONAL_CONSULTANT);
    when(sessionService.getSession(Mockito.anyLong()))
        .thenReturn(OPTIONAL_SESSION_WITHOUT_CONSULTANT);
    when(authenticatedUser.getGrantedAuthorities()).thenReturn(AUTHORITY_ASSIGN_SESSION);

    mvc.perform(put(PATH_PUT_ASSIGN_SESSION).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    verify(logService, atLeastOnce()).logForbidden(Mockito.anyString());
  }

  @Test
  public void registerUser_Should_DecodePassword() throws Exception {

    KeycloakCreateUserResponseDTO response = new KeycloakCreateUserResponseDTO(USER_ID);
    when(createUserFacade.createUserAndInitializeAccount(Mockito.any())).thenReturn(response);
    when(consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_SUCHT))
        .thenReturn(CONSULTING_TYPE_SETTINGS_WITHOUT_MANDATORY_FIELDS);

    mvc.perform(post(PATH_REGISTER_USER).content(VALID_USER_REQUEST_BODY_WITH_ENCODED_PASSWORD)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    ArgumentCaptor<UserDTO> argument = ArgumentCaptor.forClass(UserDTO.class);
    verify(createUserFacade, times(1)).createUserAndInitializeAccount(argument.capture());
    assertEquals(DECODED_PASSWORD, argument.getValue().getPassword());

  }

  /**
   * updatePassword()
   * 
   */

  @Test
  public void updatePassword_Should_ReturnInternalServerError_WhenKeycloakDoesntRespond()
      throws Exception {

    mvc.perform(put(PATH_PUT_UPDATE_PASSWORD).content(VALID_PASSWORT_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void updatePassword_Should_ReturnInternalServerErrorAndNotChangePassword_WhenKeycloakLogoutFails()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(authenticatedUser.getUsername()).thenReturn(USERNAME);
    when(keycloakService.loginUser(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(LOGIN_RESPONSE_ENTITY_OK);
    when(keycloakService.logoutUser(Mockito.anyString())).thenReturn(false);

    mvc.perform(put(PATH_PUT_UPDATE_PASSWORD).content(VALID_PASSWORT_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    verify(keycloakService, times(0)).changePassword(Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void updatePassword_Should_ReturnInternalServerError_WhenUpdatingThePasswordFailsInKeycloak()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(authenticatedUser.getUsername()).thenReturn(USERNAME);
    when(keycloakService.loginUser(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(LOGIN_RESPONSE_ENTITY_OK);
    when(keycloakService.logoutUser(Mockito.anyString())).thenReturn(true);
    when(keycloakService.changePassword(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(false);

    mvc.perform(put(PATH_PUT_UPDATE_PASSWORD).content(VALID_PASSWORT_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void updatePassword_Should_ReturnBadRequestAndNotChangePassword_WhenOldPasswordIsInvalid()
      throws Exception {

    when(authenticatedUser.getUsername()).thenReturn(USERNAME);
    when(keycloakService.loginUser(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(LOGIN_RESPONSE_ENTITY_BAD_REQUEST);

    mvc.perform(put(PATH_PUT_UPDATE_PASSWORD).content(VALID_PASSWORT_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    verify(keycloakService, times(0)).changePassword(Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void updatePassword_Should_ReturnOK_WhenUpdatingThePasswordWasSuccessful()
      throws Exception {

    when(authenticatedUser.getUsername()).thenReturn(USERNAME);
    when(authenticatedUser.getUserId()).thenReturn(USER_ID);
    when(keycloakService.loginUser(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(LOGIN_RESPONSE_ENTITY_OK);
    when(keycloakService.logoutUser(Mockito.anyString())).thenReturn(true);
    when(keycloakService.changePassword(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

    mvc.perform(put(PATH_PUT_UPDATE_PASSWORD).content(VALID_PASSWORT_REQUEST_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.OK.value()));
  }

  @Test
  public void updateKey_Should_ReturnConflict_WhenProvidedWithSameKey() throws Exception {

    when(encryptionService.getMasterKey()).thenReturn(MASTER_KEY_1);

    mvc.perform(post(PATH_UPDATE_KEY).contentType(MediaType.APPLICATION_JSON)
        .content(MASTER_KEY_DTO_KEY_1).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
  }

  @Test
  public void updateKey_Should_ReturnAccepted_WhenProvidedWithNewKey() throws Exception {

    when(encryptionService.getMasterKey()).thenReturn(MASTER_KEY_1);

    mvc.perform(post(PATH_UPDATE_KEY).contentType(MediaType.APPLICATION_JSON)
        .content(MASTER_KEY_DTO_KEY_2).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  /**
   * Method: createChat (role: kreuzbund-consultant)
   * 
   */

  @Test
  public void createChat_Should_ReturnBadRequest_WhenQueryParamsAreInvalid() throws Exception {

    mvc.perform(post(PATH_POST_CHAT_NEW).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    verifyNoMoreInteractions(chatService);
    verifyNoMoreInteractions(consultantService);
  }

  @Test
  public void createChat_Should_ReturnInternalServerErrorAndLogError_When_ChatCouldNotBeCreated()
      throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultant(Mockito.anyString())).thenReturn(OPTIONAL_CONSULTANT);
    when(createChatFacade.createChat(Mockito.any(), Mockito.any())).thenReturn(null);

    mvc.perform(post(PATH_POST_CHAT_NEW).content(VALID_CREATE_CHAT_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
  }

  @Test
  public void createChat_Should_ReturnCreated_When_ChatWasCreated() throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(OPTIONAL_CONSULTANT);
    when(createChatFacade.createChat(Mockito.any(), Mockito.any()))
        .thenReturn(CREATE_CHAT_RESPONSE_DTO);

    mvc.perform(post(PATH_POST_CHAT_NEW).content(VALID_CREATE_CHAT_BODY)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.CREATED.value()));
  }

  /**
   * Method: startChat
   * 
   */
  @Test
  public void startChat_Should_ReturnBadRequest_WhenPathParamsAreInvalid() throws Exception {
    /**
     * Method: stopChat (role: kreuzbund-consultant)
     * 
     */

    mvc.perform(put(PATH_PUT_CHAT_START_WITH_INVALID_PATH_PARAMS)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verifyNoMoreInteractions(chatService);
    verifyNoMoreInteractions(rocketChatService);
    verifyNoMoreInteractions(startChatFacade);
    verifyNoMoreInteractions(chatHelper);
  }

  @Test
  public void startChat_Should_ReturnOK_When_ChatWasStarted() throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(OPTIONAL_CONSULTANT);
    when(chatService.getChat(Mockito.any())).thenReturn(Optional.of(INACTIVE_CHAT));

    mvc.perform(put(PATH_PUT_CHAT_START).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }

  @Test
  public void startChat_Should_ReturnBadRequest_When_ChatWasNotFound() throws Exception {

    when(authenticatedUser.getUserId()).thenReturn(CONSULTANT_ID);
    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any(AuthenticatedUser.class)))
        .thenReturn(Optional.empty());
    when(chatService.getChat(Mockito.any())).thenReturn(Optional.empty());

    mvc.perform(put(PATH_PUT_CHAT_START).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
  }

  /**
   * Method: getChat
   * 
   */
  @Test
  public void getChat_Should_ReturnBadRequest_WhenPathParamsAreInvalid() throws Exception {

    mvc.perform(get(PATH_GET_CHAT_WITH_INVALID_PATH_PARAMS).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    verifyNoMoreInteractions(getChatFacade);
    verifyNoMoreInteractions(chatService);
    verifyNoMoreInteractions(consultantService);
    verifyNoMoreInteractions(userService);
    verifyNoMoreInteractions(chatHelper);
  }

  @Test
  public void getChat_Should_ReturnOk_When_RequestOk() throws Exception {

    mvc.perform(get(PATH_GET_CHAT).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    verify(getChatFacade, times(1)).getChat(Mockito.any(), Mockito.any());
  }

  /**
   * Method: joinChat
   * 
   */
  @Test
  public void joinChat_Should_ReturnBadRequest_WhenPathParamsAreInvalid() throws Exception {

    mvc.perform(put(PATH_PUT_JOIN_CHAT_WITH_INVALID_PATH_PARAMS)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verifyNoMoreInteractions(joinAndLeaveChatFacade);
    verifyNoMoreInteractions(chatService);
    verifyNoMoreInteractions(consultantService);
    verifyNoMoreInteractions(userService);
    verifyNoMoreInteractions(chatHelper);
  }

  @Test
  public void joinChat_Should_ReturnOk_When_ChatWasJoined() throws Exception {

    mvc.perform(put(PATH_PUT_JOIN_CHAT).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    verify(joinAndLeaveChatFacade, times(1)).joinChat(Mockito.any(), Mockito.any());
  }

  @Test
  public void stopChat_Should_ReturnBadRequest_WhenQueryParamsAreInvalid() throws Exception {

    mvc.perform(put(PATH_PUT_CHAT_STOP_INVALID).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    verifyNoMoreInteractions(chatService);
    verifyNoMoreInteractions(consultantService);
    verifyNoMoreInteractions(stopChatFacade);
  }

  @Test
  public void stopChat_Should_ReturnInternalServerError_When_CallingConsultantDoesNotExist()
      throws Exception {

    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any()))
        .thenThrow(new InternalServerErrorException());

    mvc.perform(put(PATH_PUT_CHAT_STOP).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    verifyNoMoreInteractions(chatService);
    verifyNoMoreInteractions(stopChatFacade);
  }

  @Test
  public void stopChat_Should_ReturnBadRequest_When_ChatNotFound() throws Exception {

    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any()))
        .thenReturn(OPTIONAL_CONSULTANT);
    when(chatService.getChat(Mockito.anyLong())).thenReturn(Optional.empty());

    mvc.perform(put(PATH_PUT_CHAT_STOP).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  public void stopChat_Should_ReturnOk_When_ChatWasStopped() throws Exception {

    when(consultantService.getConsultantViaAuthenticatedUser(Mockito.any()))
        .thenReturn(OPTIONAL_CONSULTANT);
    when(chatService.getChat(Mockito.anyLong())).thenReturn(Optional.of(chat));

    mvc.perform(put(PATH_PUT_CHAT_STOP).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(HttpStatus.OK.value()));
  }

  /**
   * Method: getChat
   * 
   */
  @Test
  public void getChatMembers_Should_ReturnBadRequest_WhenPathParamsAreInvalid() throws Exception {

    mvc.perform(get(PATH_GET_CHAT_MEMBERS_WITH_INVALID_PATH_PARAMS)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verifyNoMoreInteractions(getChatMembersFacade);
    verifyNoMoreInteractions(chatService);
    verifyNoMoreInteractions(consultantService);
    verifyNoMoreInteractions(userService);
    verifyNoMoreInteractions(chatHelper);
    verifyNoMoreInteractions(userHelper);
    verifyNoMoreInteractions(rocketChatService);
  }

  @Test
  public void getChatMembers_Should_ReturnOk_When_RequestOk() throws Exception {

    mvc.perform(get(PATH_GET_CHAT_MEMBERS).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    verify(getChatMembersFacade, times(1)).getChatMembers(Mockito.any(), Mockito.any());
  }

  /**
   * Method: updateChat
   * 
   */
  @Test
  public void updateChat_Should_ReturnBadRequest_WhenPathParamsAreInvalid() throws Exception {

    mvc.perform(put(PATH_PUT_UPDATE_CHAT_INVALID_PATH_PARAMS)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    verifyNoMoreInteractions(chatService);
  }

  @Test
  public void updateChat_Should_ReturnOk_When_RequestOk() throws Exception {

    mvc.perform(put(PATH_PUT_UPDATE_CHAT).contentType(MediaType.APPLICATION_JSON)
        .content(VALID_UPDATE_CHAT_BODY).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(chatService, times(1)).updateChat(Mockito.anyLong(), Mockito.any(), Mockito.any());
  }

}
