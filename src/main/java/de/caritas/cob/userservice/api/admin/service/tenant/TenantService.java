package de.caritas.cob.userservice.api.admin.service.tenant;

import de.caritas.cob.userservice.api.config.CacheManagerConfig;
import de.caritas.cob.userservice.tenantservice.generated.web.TenantControllerApi;
import de.caritas.cob.userservice.tenantservice.generated.web.model.RestrictedTenantDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {

  private final @NonNull TenantControllerApi tenantControllerApi;

  @Cacheable(cacheNames = CacheManagerConfig.TENANT_CACHE, key = "#subdomain")
  public RestrictedTenantDTO getRestrictedTenantData(String subdomain) {
    return tenantControllerApi.getRestrictedTenantDataBySubdomain(subdomain);
  }

  @Cacheable(cacheNames = CacheManagerConfig.TENANT_CACHE, key = "#tenantId")
  public RestrictedTenantDTO getRestrictedTenantData(Long tenantId) {
    return tenantControllerApi
        .getRestrictedTenantDataByTenantId(tenantId);
  }

}
