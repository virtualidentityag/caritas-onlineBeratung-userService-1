package de.caritas.cob.userservice.api.tenant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@ExtendWith(MockitoExtension.class)
class AccessTokenTenantResolverTest {

  @InjectMocks AccessTokenTenantResolver accessTokenTenantResolver;

  @Mock SecurityContext mockSecurityContext;

  @Mock Authentication mockAuthentication;

  @Mock HttpServletRequest authenticatedRequest;

  @AfterEach
  public void tearDown() {
    SecurityContextHolder.clearContext();
  }

  private void givenUserIsAuthenticated() {
    SecurityContextHolder.setContext(mockSecurityContext);
    when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
    Jwt jwt = buildJwt();
    when(mockAuthentication.getPrincipal()).thenReturn(jwt);
  }

  @Test
  void resolve_Should_ResolveTenantId_When_TenantIdInAccessTokenClaim() {
    // given
    givenUserIsAuthenticated();

    // when
    Optional<Long> resolvedTenantId = accessTokenTenantResolver.resolve(authenticatedRequest);

    // then
    assertThat(resolvedTenantId).isEqualTo(Optional.of(1L));
  }

  private Jwt buildJwt() {
    Map<String, Object> headers = new HashMap<>();
    headers.put("alg", "HS256"); // Signature algorithm
    headers.put("typ", "JWT"); // Token type
    return new Jwt(
        "token",
        Instant.now(),
        Instant.now().plusSeconds(1),
        headers,
        givenClaimMapContainingTenantId(1));
  }

  private HashMap<String, Object> givenClaimMapContainingTenantId(Integer tenantId) {
    HashMap<String, Object> claimMap = Maps.newHashMap();
    claimMap.put("tenantId", tenantId);
    return claimMap;
  }
}
