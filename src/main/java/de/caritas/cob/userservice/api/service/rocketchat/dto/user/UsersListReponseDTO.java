package de.caritas.cob.userservice.api.service.rocketchat.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.caritas.cob.userservice.api.service.rocketchat.dto.RocketChatUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response wrapper object for Rocket.Chat API Call for retrieving all users.
 * https://developer.rocket.chat/api/rest-api/endpoints/users/list
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersListReponseDTO {

  @JsonProperty("users")
  private RocketChatUserDTO[] users;

}
