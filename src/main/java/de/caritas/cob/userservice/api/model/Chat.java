package de.caritas.cob.userservice.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.caritas.cob.userservice.api.exception.httpresponses.InternalServerErrorException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import java.util.Objects;
import java.util.Set;
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

@Entity
@Table(name = "chat")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Chat {

  public enum ChatInterval {
    WEEKLY
  }

  @Id
  @SequenceGenerator(name = "id_seq", allocationSize = 1, sequenceName = "sequence_chat")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "topic", nullable = false)
  @Size(max = 255)
  @NonNull
  private String topic;

  @Column(name = "consulting_type", updatable = false, columnDefinition = "tinyint")
  private Integer consultingTypeId;

  @Column(name = "initial_start_date", nullable = false)
  @NonNull
  private LocalDateTime initialStartDate;

  @Column(name = "start_date", nullable = false)
  @NonNull
  private LocalDateTime startDate;

  @Column(name = "duration", nullable = false, columnDefinition = "smallint")
  private int duration;

  @Column(name = "is_repetitive", nullable = false)
  private boolean repetitive;

  @Enumerated(EnumType.STRING)
  @Column(name = "chat_interval")
  private ChatInterval chatInterval;

  @Column(name = "is_active", nullable = false)
  private boolean active;

  @Column(name = "max_participants", columnDefinition = "tinyint NULL")
  private Integer maxParticipants;

  @Column(name = "rc_group_id")
  private String groupId;

  @ManyToOne
  @JoinColumn(name = "consultant_id_owner", nullable = false)
  @Fetch(FetchMode.SELECT)
  private Consultant chatOwner;

  @OneToMany(mappedBy = "chat", orphanRemoval = true)
  @Exclude
  private Set<ChatAgency> chatAgencies;

  @OneToMany(mappedBy = "chat", orphanRemoval = true)
  @Exclude
  private Set<UserChat> chatUsers;

  @Column(name = "update_date")
  private LocalDateTime updateDate;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(name = "hint_message")
  private String hintMessage;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Chat)) {
      return false;
    }
    var chat = (Chat) o;
    return id.equals(chat.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @JsonIgnore
  public LocalDateTime nextStart() {
    if (!repetitive) {
      return null;
    }

    if (!ChatInterval.WEEKLY.equals(chatInterval)) {
      var message = "Repetitive chat with id %s does not have a valid interval.";
      throw new InternalServerErrorException(message.formatted(id));
    }

    return startDate.plusWeeks(1);
  }
}
