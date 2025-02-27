package de.caritas.cob.userservice.api.adapters.rocketchat.config;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.caritas.cob.userservice.api.adapters.rocketchat.RocketChatCredentials;
import de.caritas.cob.userservice.api.exception.httpresponses.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import java.util.Arrays;
import lombok.Data;
import org.apache.logging.log4j.core.util.CronExpression;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.WebUtils;

@Data
@Configuration
@Validated
@ConfigurationProperties(prefix = "rocket-chat")
public class RocketChatConfig {

  private static final String CONTENT_TYPE = "Content-Type";

  private static final char PATH_SEPARATOR = '/';

  private final HttpServletRequest httpServletRequest;

  @URL private String baseUrl;

  @NotBlank private String credentialCron;

  @NotBlank private String mongoUrl;

  @Bean("rocketChatRestTemplate")
  RestTemplate rocketChatRestTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
        .defaultHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
        .build();
  }

  @Bean
  @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  RocketChatCredentials rocketChatCredentials() {
    var rcUserId = httpServletRequest.getHeader("RCUserId");

    return RocketChatCredentials.builder()
        .rocketChatToken(rcToken())
        .rocketChatUserId(rcUserId)
        .build();
  }

  @Bean
  MongoClient mongoClient() {
    var connectionString = new ConnectionString(mongoUrl);
    var settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();

    return MongoClients.create(settings);
  }

  private String rcToken() {
    var rcToken = httpServletRequest.getHeader("rcToken");
    if (isNull(rcToken)) {
      var cookie = WebUtils.getCookie(httpServletRequest, "rc_token");
      if (nonNull(cookie)) {
        rcToken = cookie.getValue();
      } else {
        throw new BadRequestException("Neither rcToken in header nor rc_token in cookie.");
      }
    }

    return rcToken;
  }

  @AssertTrue
  @SuppressWarnings("unused")
  private boolean isCronExpression() {
    return CronExpression.isValidExpression(credentialCron);
  }

  public String getApiUrl(String path) {
    return getApiUrl(path, "");
  }

  public String getApiUrl(String path, String arg) {
    var queryParams = fromUriString(baseUrl + path).build().getQueryParams();
    if (!queryParams.isEmpty()) {
      path = path.substring(0, path.indexOf("?"));
    }

    var builder = fromUriString(baseUrl);
    Arrays.stream(
            StringUtils.trimLeadingCharacter(path, PATH_SEPARATOR)
                .replaceAll("(\\{).*(})", arg)
                .split(Character.toString(PATH_SEPARATOR)))
        .forEach(builder::pathSegment);
    queryParams.forEach(builder::queryParam);

    return builder.toUriString();
  }
}
