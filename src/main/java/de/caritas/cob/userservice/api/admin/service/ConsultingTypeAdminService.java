package de.caritas.cob.userservice.api.admin.service;

import de.caritas.cob.userservice.api.model.NewMessageDTO;
import de.caritas.cob.userservice.api.model.NotificationDTO;
import de.caritas.cob.userservice.api.model.RoleDTO;
import de.caritas.cob.userservice.consultingtypeservice.generated.web.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.userservice.api.admin.hallink.ConsultingTypePaginationLinksBuilder;
import de.caritas.cob.userservice.api.manager.consultingtype.ConsultingTypeManager;
import de.caritas.cob.userservice.api.model.ConsultingTypeAdminResultDTO;
import de.caritas.cob.userservice.api.model.ConsultingTypeResultDTO;
import de.caritas.cob.userservice.api.model.PaginationLinks;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

/**
 * Service class to handle administrative operations on consulting types.
 */
@Service
@RequiredArgsConstructor
public class ConsultingTypeAdminService {

  private final @NonNull ConsultingTypeManager consultingTypeManager;

  /**
   * Returns all dioceses within the given page and perPage offsets.
   *
   * @param page    Number of page where to start in the query (1 = first page) (required) * @param
   * @param perPage Number of items which are being returned per page (required)
   * @return {@link ConsultingTypeAdminResultDTO}
   */
  public ConsultingTypeAdminResultDTO findConsultingTypes(Integer page, Integer perPage) {
    PagedListHolder<ConsultingTypeResultDTO> pagedListHolder = new PagedListHolder<>(
        fullSortedConsultingTypeResponseList());
    pagedListHolder.setPageSize(Math.max(perPage, 1));
    pagedListHolder.setPage(currentPage(page, pagedListHolder));

    return new ConsultingTypeAdminResultDTO()
        .embedded(page > pagedListHolder.getPageCount() ? Collections.emptyList()
            : pagedListHolder.getPageList())
        .links(buildPaginationLinks(page, perPage, pagedListHolder))
        .total(pagedListHolder.getNrOfElements());
  }

  private List<ConsultingTypeResultDTO> fullSortedConsultingTypeResponseList() {
    return null;
        /*consultingTypeManager.getConsultingTypeSettingsMap().values().stream().sorted(
        Comparator.comparing(ExtendedConsultingTypeResponseDTO::getConsultingTypeUrlName))
        .map(this::fromConsultingTypeSettings)
        .collect(Collectors.toList());*/
  }

  private Integer currentPage(Integer page,
      PagedListHolder<ConsultingTypeResultDTO> pagedListHolder) {
    return Math.max(page < pagedListHolder.getPageCount() ? page - 1 : page, 0);
  }

  private ConsultingTypeResultDTO fromConsultingTypeSettings(
      ExtendedConsultingTypeResponseDTO ctSettings) {
    return new ConsultingTypeResultDTO()
        .id(ctSettings.getId())
        .name(ctSettings.getSlug())
        .languageFormal(ctSettings.getLanguageFormal())
        .roles(null)
        .sendWelcomeMessage(ctSettings.getWelcomeMessage().getSendWelcomeMessage())
        .welcomeMessage(ctSettings.getWelcomeMessage().getWelcomeMessageText())
        .monitoring(ctSettings.getMonitoring().getInitializeMonitoring())
        .feedbackChat(ctSettings.getInitializeFeedbackChat().booleanValue())
        .notifications(null);
  }

  private PaginationLinks buildPaginationLinks(Integer page, Integer perPage,
      PagedListHolder<ConsultingTypeResultDTO> pagedListHolder) {
    return ConsultingTypePaginationLinksBuilder
        .getInstance()
        .withPage(page)
        .withPerPage(perPage)
        .withPagedListHolder(pagedListHolder)
        .buildPaginationLinks();
  }
}
