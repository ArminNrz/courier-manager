package ir.ronad.courierManager.security.properties;

import lombok.Getter;

@Getter
public class AuthenticationProperties {

    private final JwtProperties jwt = new JwtProperties();
}
