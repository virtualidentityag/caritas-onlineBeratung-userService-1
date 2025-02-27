package de.caritas.cob.userservice.api.adapters.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.caritas.cob.userservice.api.adapters.web.dto.serialization.DecodeUsernameJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Consultant object for a session representing the assigned consultant (for the user session list
 * call)
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "SessionConsultantForUser")
public class SessionConsultantForUserDTO {

  private String consultantId;

  @Schema(example = "\"Username\"")
  @JsonSerialize(using = DecodeUsernameJsonSerializer.class)
  private String username;

  @Schema(example = "\"true\"")
  private boolean isAbsent;

  @Schema(example = "\"Bin nicht da\"")
  private String absenceMessage;

  private String displayName;
}
