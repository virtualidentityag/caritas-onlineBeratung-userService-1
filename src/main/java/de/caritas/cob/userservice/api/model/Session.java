package de.caritas.cob.userservice.api.model;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.LanguageCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.springframework.lang.Nullable;

@Entity
@Builder
@Table(name = "SESSION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class Session implements TenantAware {

  public enum RegistrationType {
    REGISTERED,
    ANONYMOUS
  }

  @AllArgsConstructor
  @Getter
  @JsonFormat(shape = JsonFormat.Shape.NUMBER)
  public enum SessionStatus {
    INITIAL(0),
    NEW(1),
    IN_PROGRESS(2),
    DONE(3),
    IN_ARCHIVE(4);

    private final int value;

    public static Optional<SessionStatus> valueOf(int value) {
      return Arrays.stream(SessionStatus.values())
          .filter(legNo -> legNo.value == value)
          .findFirst();
    }

    public static boolean isStatusValueInProgress(int value) {
      return value == IN_PROGRESS.getValue();
    }
  }

  /** Represents a session of a user */
  public Session(
      User user,
      int consultingTypeId,
      @NonNull String postcode,
      Long agencyId,
      @NonNull SessionStatus status,
      boolean teamSession) {
    this.user = user;
    this.consultingTypeId = consultingTypeId;
    this.postcode = postcode;
    this.agencyId = agencyId;
    this.status = status;
    this.teamSession = teamSession;
    this.registrationType = RegistrationType.REGISTERED;
  }

  @Id
  @SequenceGenerator(name = "id_seq", allocationSize = 1, sequenceName = "sequence_session")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "consultant_id")
  @Fetch(FetchMode.SELECT)
  private Consultant consultant;

  @Column(
      name = "consulting_type",
      updatable = false,
      nullable = false,
      columnDefinition = "tinyint")
  private int consultingTypeId;

  @Column(
      name = "registration_type",
      updatable = false,
      nullable = false,
      columnDefinition = "varchar(20) not null default 'REGISTERED'")
  @Enumerated(EnumType.STRING)
  @NonNull
  private RegistrationType registrationType;

  @Column(name = "postcode", nullable = false)
  @Size(max = 5)
  @NonNull
  private String postcode;

  @Column(name = "agency_id")
  private Long agencyId;

  @Enumerated(EnumType.STRING)
  @Column(
      name = "language_code",
      columnDefinition = "varchar(2) not null default 'de'",
      length = 2,
      nullable = false)
  private LanguageCode languageCode;

  @NonNull
  @Column(columnDefinition = "tinyint")
  private SessionStatus status;

  @Column(name = "message_date")
  @Nullable
  private LocalDateTime enquiryMessageDate;

  @Column(name = "rc_group_id")
  private String groupId;

  @Column(name = "rc_feedback_group_id")
  private String feedbackGroupId;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "session")
  @Exclude
  private List<SessionData> sessionData;

  @Column(name = "is_team_session", columnDefinition = "tinyint default '0'")
  private boolean teamSession;

  @Column(name = "is_peer_chat", columnDefinition = "tinyint default '0'")
  private boolean isPeerChat;

  @Column(
      name = "is_consultant_directly_set",
      nullable = false,
      columnDefinition = "bit default false")
  private Boolean isConsultantDirectlySet;

  public boolean hasFeedbackChat() {
    return isNotBlank(feedbackGroupId);
  }

  @Column(name = "create_date", columnDefinition = "datetime")
  private LocalDateTime createDate;

  @Column(name = "update_date", columnDefinition = "datetime")
  private LocalDateTime updateDate;

  @Column(name = "tenant_id")
  private Long tenantId;

  @Column(name = "main_topic_id")
  private Long mainTopicId;

  @Column(name = "user_gender")
  private String userGender;

  @Column(name = "user_age")
  private Integer userAge;

  @Column(name = "counselling_relation")
  private String counsellingRelation;

  @Column(name = "referer")
  private String referer;

  @OneToMany(
      targetEntity = SessionTopic.class,
      mappedBy = "session",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<SessionTopic> sessionTopics;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Session)) {
      return false;
    }
    Session session = (Session) o;
    return id.equals(session.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @JsonIgnore
  public boolean isAdvisedBy(Consultant consultant) {
    return nonNull(this.consultant) && nonNull(consultant) && this.consultant.equals(consultant);
  }

  @JsonIgnore
  public boolean isAdvisedBy(String consultantId) {
    return nonNull(consultant) && nonNull(consultantId) && consultantId.equals(consultant.getId());
  }

  @JsonIgnore
  public boolean isAdvised(String adviceSeekerId) {
    return nonNull(user) && nonNull(adviceSeekerId) && adviceSeekerId.equals(user.getUserId());
  }
}
