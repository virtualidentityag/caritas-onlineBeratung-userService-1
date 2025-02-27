package de.caritas.cob.userservice.api.tenant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.caritas.cob.userservice.api.service.httpheader.TenantHeaderSupplier;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomHeaderTenantResolverTest {
  @Mock HttpServletRequest request;
  @Mock TenantHeaderSupplier tenantHeaderSupplier;

  @InjectMocks CustomHeaderTenantResolver customHeaderTenantResolver;

  @Test
  void resolve_Should_ResolveTenantId_When_SupplierCanResolveTenantId() {
    // given
    when(tenantHeaderSupplier.getTenantFromHeader()).thenReturn(Optional.of(2L));
    var resolved = customHeaderTenantResolver.resolve(request);
    // then
    assertThat(resolved).isEqualTo(Optional.of(2L));
  }

  @Test
  void resolve_Should_NotResolveTenantId_When_SupplierCannotResolveTenantId() {
    // given
    when(tenantHeaderSupplier.getTenantFromHeader()).thenReturn(Optional.empty());
    var resolved = customHeaderTenantResolver.resolve(request);
    // then
    assertThat(resolved).isEmpty();
  }
}
