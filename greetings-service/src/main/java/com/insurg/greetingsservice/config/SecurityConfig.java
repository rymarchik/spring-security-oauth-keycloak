package com.insurg.greetingsservice.config;

import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;
import static org.springframework.security.authorization.AuthorizationManagers.allOf;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(configurer -> configurer.anyRequest()
                .access(allOf(hasRole("MANAGER"), hasAuthority("SCOPE_greetings"))))
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(CsrfConfigurer::disable)
            .oauth2ResourceServer(configurer -> configurer.jwt(jwt -> {
                var jwtAuthenticationConverter = new JwtAuthenticationConverter();
                jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");

                var defaultJwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                var customJwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                customJwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
                customJwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
                jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(token ->
                    Stream.concat(defaultJwtGrantedAuthoritiesConverter.convert(token).stream(),
                            customJwtGrantedAuthoritiesConverter.convert(token).stream())
                        .toList());

                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
            }))
            .build();
    }
}
