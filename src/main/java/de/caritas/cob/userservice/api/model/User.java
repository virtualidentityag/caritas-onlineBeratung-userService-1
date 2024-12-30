package de.caritas.cob.userservice.api.model;

import static de.caritas.cob.userservice.mailservice.generated.web.model.Dialect.FORMAL;
import static de.caritas.cob.userservice.mailservice.generated.web.model.Dialect.INFORMAL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.LanguageCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.Filter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** Represents a user */
@Entity
@Table(name = "`user`")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class User implements TenantAware, NotificationsAware {

  @Id
  @Column(name = "user_id", updatable = false, nullable = false)
  @Size(max = 36)
  @NonNull
  private String userId;

  @Column(name = "id_old", updatable = false)
  private Long oldId;

  @Column(name = "username", updatable = false, nullable = false)
  @Size(max = 255)
  @NonNull
  private String username;

  @Column(name = "email", nullable = false)
  @Size(max = 255)
  @NonNull
  private String email;

  @Column(name = "rc_user_id")
  private String rcUserId;

  @Column(name = "language_formal", nullable = false, columnDefinition = "tinyint")
  private boolean languageFormal;

  @OneToMany(mappedBy = "user")
  @Exclude
  private Set<Session> sessions;

  @OneToMany(mappedBy = "user")
  @Exclude
  private Set<UserAgency> userAgencies;

  @Column(name = "mobile_token")
  @Lob
  private String mobileToken;

  @OneToMany(mappedBy = "user")
  @Exclude
  private Set<UserMobileToken> userMobileTokens;

  @Column(name = "delete_date", columnDefinition = "datetime")
  private LocalDateTime deleteDate;

  @Column(name = "tenant_id")
  private Long tenantId;

  @CreatedDate
  @Column(name = "create_date", columnDefinition = "datetime")
  private LocalDateTime createDate;

  @LastModifiedDate
  @Column(name = "update_date", columnDefinition = "datetime")
  private LocalDateTime updateDate;

  @Column(name = "encourage_2fa", nullable = false, columnDefinition = "bit default true")
  private Boolean encourage2fa;

  @Enumerated(EnumType.STRING)
  @Column(length = 2, nullable = false, columnDefinition = "varchar(2) default 'de'")
  private LanguageCode languageCode;

  @Column(name = "terms_and_conditions_confirmation", columnDefinition = "datetime")
  private LocalDateTime termsAndConditionsConfirmation;

  @Column(name = "data_privacy_confirmation", columnDefinition = "datetime")
  private LocalDateTime dataPrivacyConfirmation;

  @Column(name = "notifications_enabled", columnDefinition = "tinyint", nullable = false)
  private boolean notificationsEnabled;

  @Column(name = "notifications_settings")
  private String notificationsSettings;

  public User(
      @Size(max = 36) @NonNull String userId,
      Long oldId,
      @Size(max = 255) @NonNull String username,
      @Size(max = 255) @NonNull String email,
      boolean languageFormal) {
    this.userId = userId;
    this.oldId = oldId;
    this.username = username;
    this.email = email;
    this.languageFormal = languageFormal;
    setEncourage2fa(true);
    setLanguageCode(LanguageCode.de);
    this.termsAndConditionsConfirmation = LocalDateTime.now();
    this.dataPrivacyConfirmation = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return userId.equals(user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

  @JsonIgnore
  @Transient
  public de.caritas.cob.userservice.mailservice.generated.web.model.Dialect getDialect() {
    return isLanguageFormal() ? FORMAL : INFORMAL;
  }
}
