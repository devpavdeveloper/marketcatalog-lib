package by.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class PlatformSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public KeycloakConverter keycloakConverter() {
        return new KeycloakConverter();
    }


    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorize -> authorize
                        .antMatchers("/h2/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(keycloakConverter())));
    }

}
