package de.caritas.cob.userservice.api.adapters.keycloak.config;

import static java.util.Objects.nonNull;

import com.google.common.collect.Lists;
import de.caritas.cob.userservice.api.exception.keycloak.KeycloakException;
import de.caritas.cob.userservice.api.helper.AuthenticatedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@Data
@Configuration
@Validated
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfig {

  @Bean("keycloakRestTemplate")
  RestTemplate keycloakRestTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.build();
  }

  public Collection<String> extractRealmRoles(Jwt jwt) {
    Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
    if (realmAccess != null) {
      var roles = (List<String>) realmAccess.get("roles");
      if (roles != null) {
        return roles;
      }
    }
    return Lists.newArrayList();
  }

  @Bean
  @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  AuthenticatedUser authenticatedUser(HttpServletRequest request) {
    var userPrincipal = request.getUserPrincipal();
    var authenticatedUser = new AuthenticatedUser();

    if (nonNull(userPrincipal)) {
      var authToken = (JwtAuthenticationToken) userPrincipal;
      var token = authToken.getToken(); // Extract the JWT token from the authentication token
      var claimMap = token.getClaims(); // Retrieve claims from the JWT token

      try {
        if (claimMap.containsKey("username")) {
          authenticatedUser.setUsername(claimMap.get("username").toString());
        }
        authenticatedUser.setUserId(claimMap.get("userId").toString());
        authenticatedUser.setAccessToken(token.getTokenValue());
        authenticatedUser.setRoles(extractRealmRoles(token).stream().collect(Collectors.toSet()));
      } catch (Exception exception) {
        throw new KeycloakException("Keycloak data missing.", exception);
      }

      var authorities =
          SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
              .map(Object::toString)
              .collect(Collectors.toSet());
      authenticatedUser.setGrantedAuthorities(authorities);
    }

    return authenticatedUser;
  }

  @Bean
  Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(authServerUrl)
        .realm(realm)
        .username(config.getAdminUsername())
        .password(config.getAdminPassword())
        .clientId(config.getAdminClientId())
        .build();
  }

  /**
   * Use the KeycloakSpringBootConfigResolver to be able to save the Keycloak settings in the spring
   * application properties.
   */
  @Bean
  KeycloakConfigResolver keyCloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  @URL private String authServerUrl;

  @NotBlank private String realm;

  @NotBlank private String resource;

  @NotBlank private String principalAttribute;

  @NotNull private Boolean cors;

  private KeycloakCustomConfig config;
}
