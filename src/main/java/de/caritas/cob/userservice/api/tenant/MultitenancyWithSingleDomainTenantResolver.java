package de.caritas.cob.userservice.api.tenant;

import de.caritas.cob.userservice.api.adapters.web.dto.AgencyDTO;
import de.caritas.cob.userservice.api.exception.httpresponses.BadRequestException;
import de.caritas.cob.userservice.api.service.agency.AgencyService;
import de.caritas.cob.userservice.api.service.httpheader.HttpHeadersResolver;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MultitenancyWithSingleDomainTenantResolver implements TenantResolver {
  @Value("${feature.multitenancy.with.single.domain.enabled}")
  private boolean multitenancyWithSingleDomain;

  private final @NonNull AgencyService agencyService;

  private final @NonNull HttpHeadersResolver httpHeadersResolver;

  @Override
  public Optional<Long> resolve(HttpServletRequest request) {
    if (multitenancyWithSingleDomain) {
      return resolveTenantFromAgency();
    } else {
      return Optional.empty();
    }
  }

  private Optional<Long> resolveTenantFromAgency() {
    Optional<Long> agencyId = httpHeadersResolver.findHeaderValue("agencyId");
    if (agencyId.isEmpty()) {
      log.debug("Agency id is empty, multitenancyWithSingleDomainTenantResolver does not resolve");
      return Optional.empty();
    }
    AgencyDTO agency = agencyService.getAgency(agencyId.get());
    validateResolvedAgencyContainsTenant(agency);
    return Optional.of(agency.getTenantId());
  }

  private void validateResolvedAgencyContainsTenant(AgencyDTO agency) {
    if (agency.getTenantId() == null) {
      throw new BadRequestException(
          "Cannot resolve tenant, as the resolved agency has null tenantId!");
    }
  }

  @Override
  public boolean canResolve(HttpServletRequest request) {
    return resolve(request).isPresent();
  }
}
