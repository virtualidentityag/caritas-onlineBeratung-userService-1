package de.caritas.cob.userservice.api.service;

import de.caritas.cob.userservice.api.repository.TenantAware;
import de.caritas.cob.userservice.api.tenant.TenantContext;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantHibernateInterceptor extends EmptyInterceptor {

  private final Long TECHNICAL_TENANT_ID = 0L;

  @Override
  public void preFlush(Iterator entities) {
    Object entity;
    while (entities.hasNext()) {
      entity = entities.next();
      if (entity instanceof TenantAware) {
        var tenantAware = (TenantAware) entity;
        if (tenantAware.getTenantId() == null && !Long.valueOf(TECHNICAL_TENANT_ID)
            .equals(TenantContext.getCurrentTenant())) {
          ((TenantAware) entity).setTenantId(TenantContext.getCurrentTenant());
        }
      }
    }

    super.preFlush(entities);
  }
}
