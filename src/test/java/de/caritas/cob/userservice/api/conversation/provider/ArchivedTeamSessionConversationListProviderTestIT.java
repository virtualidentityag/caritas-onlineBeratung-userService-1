package de.caritas.cob.userservice.api.conversation.provider;

import static de.caritas.cob.userservice.api.conversation.model.ConversationListType.ARCHIVED_TEAM_SESSION;
import static de.caritas.cob.userservice.api.model.Session.RegistrationType.REGISTERED;
import static de.caritas.cob.userservice.api.testHelper.TestConstants.CONSULTING_TYPE_ID_OFFENDER;
import static java.util.Objects.nonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.google.api.client.util.Lists;
import com.neovisionaries.i18n.LanguageCode;
import de.caritas.cob.userservice.api.UserServiceApplication;
import de.caritas.cob.userservice.api.adapters.web.dto.ConsultantSessionListResponseDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.ConsultantSessionResponseDTO;
import de.caritas.cob.userservice.api.conversation.model.ConversationListType;
import de.caritas.cob.userservice.api.conversation.model.PageableListRequest;
import de.caritas.cob.userservice.api.model.Consultant;
import de.caritas.cob.userservice.api.model.ConsultantAgency;
import de.caritas.cob.userservice.api.model.Session;
import de.caritas.cob.userservice.api.model.Session.SessionStatus;
import de.caritas.cob.userservice.api.model.User;
import de.caritas.cob.userservice.api.port.out.ConsultantAgencyRepository;
import de.caritas.cob.userservice.api.port.out.ConsultantRepository;
import de.caritas.cob.userservice.api.port.out.SessionRepository;
import de.caritas.cob.userservice.api.port.out.UserRepository;
import de.caritas.cob.userservice.api.service.user.UserAccountService;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.iterators.PeekingIterator;
import org.apache.commons.lang3.RandomStringUtils;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = UserServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
@AutoConfigureTestDatabase(replace = Replace.ANY)
class ArchivedTeamSessionConversationListProviderTestIT {

  @Autowired
  private ArchivedTeamSessionConversationListProvider archivedTeamSessionConversationListProvider;

  @Autowired private SessionRepository sessionRepository;

  @Autowired private ConsultantRepository consultantRepository;

  @Autowired private ConsultantAgencyRepository consultantAgencyRepository;

  @Autowired private UserRepository userRepository;

  @MockBean private UserAccountService userAccountProvider;

  @AfterEach
  void cleanDatabase() {
    this.sessionRepository.deleteAll();
    this.consultantAgencyRepository.deleteAll();
    this.consultantRepository.deleteAll();
  }

  @Test
  void
      buildConversations_Should_returnExpectedResponseDTO_When_consultantHasArchivedTeamSessions() {
    saveTestData(10);
    PageableListRequest request = PageableListRequest.builder().count(5).offset(0).build();

    ConsultantSessionListResponseDTO responseDTO =
        this.archivedTeamSessionConversationListProvider.buildConversations(request);

    assertThat(responseDTO.getCount(), is(5));
    assertThat(responseDTO.getOffset(), is(0));
    assertThat(responseDTO.getTotal(), is(10));
    assertThat(responseDTO.getSessions(), hasSize(5));
  }

  @Test
  void buildConversations_Should_returnExpectedElements_When_paginationParamsAreAtTheEnd() {
    saveTestData(10);
    PageableListRequest request = PageableListRequest.builder().count(3).offset(9).build();

    ConsultantSessionListResponseDTO responseDTO =
        this.archivedTeamSessionConversationListProvider.buildConversations(request);

    assertThat(responseDTO.getCount(), is(1));
    assertThat(responseDTO.getOffset(), is(9));
    assertThat(responseDTO.getTotal(), is(10));
    assertThat(responseDTO.getSessions(), hasSize(1));
  }

  @Test
  void buildConversations_Should_returnElementsInExpectedOrder() {
    saveTestData(100);
    PageableListRequest request = PageableListRequest.builder().count(100).offset(0).build();

    ConsultantSessionListResponseDTO responseDTO =
        this.archivedTeamSessionConversationListProvider.buildConversations(request);

    PeekingIterator<ConsultantSessionResponseDTO> peeker =
        new PeekingIterator<>(responseDTO.getSessions().iterator());
    while (peeker.hasNext()) {
      ConsultantSessionResponseDTO current = peeker.next();
      ConsultantSessionResponseDTO next = peeker.peek();
      if (nonNull(next)) {
        assertThat(
            (Date) next.getLatestMessage(),
            greaterThanOrEqualTo((Date) current.getLatestMessage()));
      }
    }
  }

  @Test
  void providedType_Should_return_archivedTeamSession() {
    ConversationListType conversationListType =
        this.archivedTeamSessionConversationListProvider.providedType();

    assertThat(conversationListType, is(ARCHIVED_TEAM_SESSION));
  }

  private void saveTestData(int amount) {
    Consultant consultant = buildConsultant();
    consultantRepository.save(consultant);
    when(this.userAccountProvider.retrieveValidatedConsultant()).thenReturn(consultant);
    Consultant consultant2 = buildConsultant();
    consultantRepository.save(consultant2);

    ConsultantAgency consultantAgency = buildConsultantAgency(consultant);
    consultantAgencyRepository.save(consultantAgency);
    ConsultantAgency consultantAgency2 = buildConsultantAgency(consultant2);
    consultantAgencyRepository.save(consultantAgency2);

    List<Session> sessions =
        new EasyRandom().objects(Session.class, amount + 5).collect(Collectors.toList());
    User user = this.userRepository.findAll().iterator().next();
    sessions.forEach(
        session -> {
          session.setRegistrationType(REGISTERED);
          session.setConsultant(consultant2);
          session.setUser(user);
          session.setId(null);
          session.setSessionData(null);
          session.setTeamSession(true);
          session.setPostcode("12345");
          session.setConsultingTypeId(CONSULTING_TYPE_ID_OFFENDER);
          session.setStatus(SessionStatus.IN_ARCHIVE);
          session.setAgencyId(1L);
          session.setSessionTopics(Lists.newArrayList());
        });
    sessions.get(0).setStatus(SessionStatus.INITIAL);
    sessions.get(1).setStatus(SessionStatus.IN_PROGRESS);
    sessions.get(2).setStatus(SessionStatus.DONE);
    sessions.get(3).setStatus(SessionStatus.NEW);
    sessions.get(4).setStatus(SessionStatus.IN_ARCHIVE);
    sessions.get(4).setConsultant(consultant);
    sessions.get(4).setTeamSession(false);

    this.sessionRepository.saveAll(sessions);
  }

  private Consultant buildConsultant() {
    Consultant consultant = new Consultant();
    consultant.setId(UUID.randomUUID().toString());
    consultant.setUsername("consultant");
    consultant.setFirstName("firstname");
    consultant.setLastName("lastname");
    consultant.setEmail(RandomStringUtils.randomAlphabetic(8) + "@lastname.de");
    consultant.setTeamConsultant(false);
    consultant.setLanguageFormal(false);
    consultant.setAbsent(false);
    consultant.setEncourage2fa(true);
    consultant.setNotifyEnquiriesRepeating(true);
    consultant.setNotifyNewChatMessageFromAdviceSeeker(true);
    consultant.setNotifyNewFeedbackMessageFromAdviceSeeker(true);
    consultant.setWalkThroughEnabled(true);
    consultant.setLanguageCode(LanguageCode.de);

    return consultant;
  }

  private ConsultantAgency buildConsultantAgency(Consultant consultant) {
    ConsultantAgency consultantAgency = new ConsultantAgency();
    consultantAgency.setConsultant(consultant);
    consultantAgency.setAgencyId(1L);
    consultant.setConsultantAgencies(Collections.singleton(consultantAgency));
    return consultantAgency;
  }
}
