package ir.ronad.courierManager.security.properties;

import io.github.jhipster.config.JHipsterDefaults;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtProperties {

    private String secret = JHipsterDefaults.Security.Authentication.Jwt.secret;

    private String base64Secret = JHipsterDefaults.Security.Authentication.Jwt.base64Secret;

    private long tokenValidityInSeconds = JHipsterDefaults.Security.Authentication.Jwt
            .tokenValidityInSeconds;

    private long tokenValidityInSecondsForRememberMe = JHipsterDefaults.Security.Authentication.Jwt
            .tokenValidityInSecondsForRememberMe;
}
