package de.caritas.cob.userservice.api.port.out;

import de.caritas.cob.userservice.api.model.Chat;
import de.caritas.cob.userservice.api.model.Consultant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends CrudRepository<Chat, Long> {

  @Query(value = "SELECT c.id, c.topic, c.consulting_type, c.initial_start_date, c.start_date, "
      + "c.duration, c.is_repetitive, c.chat_interval, c.is_active, c.max_participants, "
      + "c.consultant_id_owner, c.rc_group_id, c.update_date FROM chat c JOIN chat_agency ca ON c"
      + ".id = ca.chat_id JOIN user_agency ua ON ca.agency_id = ua.agency_id AND ua.user_id = :user_id",
      nativeQuery = true)
  List<Chat> findByUserId(@Param(value = "user_id") String userId);

  @Query(value = "SELECT c.id, c.topic, c.consulting_type, c.initial_start_date, c.start_date, "
      + "c.duration, c.is_repetitive, c.chat_interval, c.is_active, c.max_participants, "
      + "c.consultant_id_owner, c.rc_group_id, c.update_date FROM chat c JOIN chat_agency ca ON c"
      + ".id = ca.chat_id AND ca.agency_id IN :agency_ids",
      nativeQuery = true)
  List<Chat> findByAgencyIds(@Param(value = "agency_ids") Set<Long> agencyIds);

  Optional<Chat> findByGroupId(String groupId);

  List<Chat> findByChatOwner(Consultant chatOwner);

  List<Chat> findAllByActiveIsTrue();
}
