package ir.ronad.courierManager.security.properties;

import io.github.jhipster.config.JHipsterDefaults;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RememberMeProperties {

    @NotNull
    private String key = JHipsterDefaults.Security.RememberMe.key;
}
