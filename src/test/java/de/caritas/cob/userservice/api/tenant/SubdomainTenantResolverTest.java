package de.caritas.cob.userservice.api.tenant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.caritas.cob.userservice.api.adapters.web.controller.interceptor.SubdomainExtractor;
import de.caritas.cob.userservice.api.admin.service.tenant.TenantService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubdomainTenantResolverTest {

  @Mock SubdomainExtractor subdomainExtractor;

  @Mock TenantService tenantService;

  @Mock HttpServletRequest httpServletRequest;

  @InjectMocks SubdomainTenantResolver subdomainTenantResolver;

  @Test
  void resolve_should_resolveTenantId_When_SubdomainCouldBeDetermined() {
    // given
    when(subdomainExtractor.getCurrentSubdomain()).thenReturn(Optional.of("mucoviscidose"));
    when(tenantService.getRestrictedTenantData("mucoviscidose"))
        .thenReturn(
            new de.caritas.cob.userservice.tenantservice.generated.web.model.RestrictedTenantDTO()
                .id(1L));

    // when
    Optional<Long> resolve = subdomainTenantResolver.resolve(httpServletRequest);

    // then
    assertThat(resolve).contains(1L);
  }

  @Test
  void resolve_should_NotResolve_When_SubdomainIsEmpty() {
    // given
    when(subdomainExtractor.getCurrentSubdomain()).thenReturn(Optional.empty());

    // when
    Optional<Long> resolve = subdomainTenantResolver.resolve(httpServletRequest);

    // then
    assertThat(resolve).isEmpty();
  }
}
