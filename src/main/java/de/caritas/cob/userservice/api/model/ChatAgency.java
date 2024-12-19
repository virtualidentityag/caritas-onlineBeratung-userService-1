package de.caritas.cob.userservice.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Represents the relation between a {@link Chat} and an agency */
@Entity
@Table(name = "chat_agency")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatAgency {

  @Id
  @SequenceGenerator(name = "id_seq", allocationSize = 1, sequenceName = "sequence_chat_agency")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "chat_id", nullable = false)
  private Chat chat;

  @Column(name = "agency_id", nullable = false)
  private Long agencyId;

  public ChatAgency(Chat chat, Long agencyId) {
    this.chat = chat;
    this.agencyId = agencyId;
  }

  @Override
  public String toString() {
    return "ChatAgency [id=" + id + ", chat=" + chat.toString() + ", agencyId=" + agencyId + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ChatAgency)) {
      return false;
    }
    ChatAgency that = (ChatAgency) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
