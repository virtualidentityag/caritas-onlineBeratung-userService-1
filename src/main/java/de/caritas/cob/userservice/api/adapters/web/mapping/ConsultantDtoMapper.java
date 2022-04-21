package de.caritas.cob.userservice.api.adapters.web.mapping;

import static java.util.Objects.isNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import de.caritas.cob.userservice.api.adapters.web.controller.UserController;
import de.caritas.cob.userservice.api.adapters.web.dto.AgencyDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.AgencyResponseDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.ConsultantAdminResponseDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.ConsultantDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.ConsultantLinks;
import de.caritas.cob.userservice.api.adapters.web.dto.ConsultantResponseDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.ConsultantSearchResultDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.HalLink;
import de.caritas.cob.userservice.api.adapters.web.dto.HalLink.MethodEnum;
import de.caritas.cob.userservice.api.adapters.web.dto.LanguageCode;
import de.caritas.cob.userservice.api.adapters.web.dto.LanguageResponseDTO;
import de.caritas.cob.userservice.api.adapters.web.dto.PaginationLinks;
import de.caritas.cob.userservice.api.adapters.web.dto.UpdateConsultantDTO;
import de.caritas.cob.userservice.api.admin.model.UpdateAdminConsultantDTO;
import de.caritas.cob.userservice.api.model.Consultant;
import de.caritas.cob.userservice.generated.api.admin.controller.UseradminApi;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsultantDtoMapper {

  public UpdateAdminConsultantDTO updateAdminConsultantOf(UpdateConsultantDTO updateConsultantDTO,
      Consultant consultant) {

    return new UpdateAdminConsultantDTO()
        .email(updateConsultantDTO.getEmail())
        .firstname(updateConsultantDTO.getFirstname())
        .lastname(updateConsultantDTO.getLastname())
        .formalLanguage(consultant.isLanguageFormal())
        .languages(languageStringsOf(updateConsultantDTO.getLanguages()))
        .absent(consultant.isAbsent())
        .absenceMessage(consultant.getAbsenceMessage());
  }

  public ConsultantResponseDTO consultantResponseDtoOf(Consultant consultant,
      List<AgencyDTO> agencies, boolean mapNames) {
    var agencyDtoList = agencies.stream()
        .map(this::agencyResponseDtoOf)
        .collect(Collectors.toList());

    var consultantResponseDto = new ConsultantResponseDTO()
        .consultantId(consultant.getId())
        .agencies(agencyDtoList);

    if (mapNames) {
      consultantResponseDto
          .firstName(consultant.getFirstName())
          .lastName(consultant.getLastName());
    }

    return consultantResponseDto;
  }

  public String languageOf(LanguageCode languageCode) {
    return isNull(languageCode) ? null : languageCode.getValue();
  }

  public List<String> languageStringsOf(List<LanguageCode> languages) {
    return isNull(languages) ? null : languages.stream()
        .map(this::languageOf)
        .collect(Collectors.toList());
  }

  public AgencyResponseDTO agencyResponseDtoOf(AgencyDTO agencyDTO) {
    return new AgencyResponseDTO()
        .id(agencyDTO.getId())
        .city(agencyDTO.getCity())
        .consultingType(agencyDTO.getConsultingType())
        .postcode(agencyDTO.getPostcode())
        .name(agencyDTO.getName())
        .description(agencyDTO.getDescription())
        .teamAgency(agencyDTO.getTeamAgency())
        .offline(agencyDTO.getOffline());
  }

  public LanguageResponseDTO languageResponseDtoOf(Set<String> languageCodes) {
    var languages = languageCodes.stream()
        .sorted()
        .map(LanguageCode::fromValue)
        .collect(Collectors.toList());

    var dto = new LanguageResponseDTO();
    dto.setLanguages(languages);

    return dto;
  }

  @SuppressWarnings("unchecked")
  public ConsultantSearchResultDTO consultantSearchResultOf(Map<String, Object> resultMap,
      String query, int page, int perPage, String field, String order) {
    var consultants = new ArrayList<ConsultantAdminResponseDTO>();

    var consultantMaps = (List<Map<String, String>>) resultMap.get("consultants");
    consultantMaps.forEach(consultantMap -> {
      var response = new ConsultantAdminResponseDTO();
      response.setEmbedded(consultantDtoOf(consultantMap));
      response.setLinks(consultantLinksOf(consultantMap));
      consultants.add(response);
    });

    var result = new ConsultantSearchResultDTO();
    result.setTotal((Integer) resultMap.get("totalElements"));
    result.setEmbedded(consultants);

    var pagination = new PaginationLinks().self(pageLinkOf(query, page, perPage, field, order));
    if (!(Boolean) resultMap.get("isFirstPage")) {
      pagination.previous(pageLinkOf(query, page - 1, perPage, field, order));
    }
    if (!(Boolean) resultMap.get("isLastPage")) {
      pagination.next(pageLinkOf(query, page + 1, perPage, field, order));
    }
    result.setLinks(pagination);

    return result;
  }

  public ConsultantDTO consultantDtoOf(Map<String, String> consultantMap) {
    var consultant = new ConsultantDTO();
    consultant.setId(consultantMap.get("id"));
    consultant.setEmail(consultantMap.get("email"));
    consultant.setFirstname(consultantMap.get("firstName"));
    consultant.setLastname(consultantMap.get("lastName"));

    return consultant;
  }

  public ConsultantLinks consultantLinksOf(Map<String, String> consultantMap) {
    var id = consultantMap.get("id");

    return new ConsultantLinks()
        .self(consultantLinkOf(id, MethodEnum.GET))
        .update(consultantLinkOf(id, MethodEnum.PUT))
        .delete(consultantLinkOf(id, MethodEnum.DELETE))
        .agencies(consultantAgencyLinkOf(id, MethodEnum.GET))
        .addAgency(consultantAgencyLinkOf(id, MethodEnum.POST));
  }

  public HalLink consultantLinkOf(String id, MethodEnum method) {
    var userAdminApi = methodOn(UseradminApi.class);
    HttpEntity<?> httpEntity;
    switch (method) {
      case PUT:
        httpEntity = userAdminApi.updateConsultant(id, null);
        break;
      case DELETE:
        httpEntity = userAdminApi.markConsultantForDeletion(id);
        break;
      default:
        httpEntity = userAdminApi.getConsultant(id);
    }

    return halLinkOf(httpEntity, method);
  }

  public HalLink consultantAgencyLinkOf(String id, MethodEnum method) {
    var userAdminApi = methodOn(UseradminApi.class);
    HttpEntity<?> httpEntity;
    if (method == MethodEnum.POST) {
      httpEntity = userAdminApi.createConsultantAgency(id, null);
    } else {
      httpEntity = userAdminApi.getConsultantAgencies(id);
    }

    return halLinkOf(httpEntity, method);
  }

  public HalLink pageLinkOf(String query, int page, int perPage, String field, String order) {
    var httpEntity = methodOn(UserController.class)
        .searchConsultants(query, page, perPage, field, order);

    return halLinkOf(httpEntity, MethodEnum.GET);
  }

  public HalLink halLinkOf(HttpEntity<?> httpEntity, MethodEnum method) {
    var link = linkTo(httpEntity).withSelfRel();

    return new HalLink()
        .href(link.getHref())
        .method(method)
        .templated(link.isTemplated());
  }
}
