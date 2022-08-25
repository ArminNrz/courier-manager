package ir.ronad.courierManager.security.properties;

import lombok.Getter;
import org.springframework.web.cors.CorsConfiguration;

@Getter
public class SecurityProperties {

    private final AuthenticationProperties authentication = new AuthenticationProperties();

    private final RememberMeProperties rememberMe = new RememberMeProperties();

    private final CorsConfiguration cors = new CorsConfiguration();
}
