package ir.ronad.courierManager.security;

import ir.ronad.courierManager.security.jwt.JwtConfigurer;
import ir.ronad.courierManager.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private final SecurityProblemSupport problemSupport;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
                .and()
                .headers()
                .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
                .and()
                .frameOptions()
                .deny()
                .and()
                .apply(new JwtConfigurer(jwtProvider));

        configureAuthorization(http);
    }

    private void configureAuthorization(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/notifications/**").permitAll()
                .antMatchers("/api/**").authenticated();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        if ((configuration.getAllowedOrigins() != null && !configuration.getAllowedOrigins().isEmpty())
                || (configuration.getAllowedOriginPatterns() != null && !configuration.getAllowedOriginPatterns().isEmpty())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", configuration);
            source.registerCorsConfiguration("/management/**", configuration);
            source.registerCorsConfiguration("/v2/api-docs", configuration);
        }
        return new CorsFilter(source);
    }
}
