package com.insurg.reactivegreetingsservice.config;

import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(customizer -> customizer.anyExchange().authenticated())
            .oauth2ResourceServer(customizer -> customizer.jwt(jwt -> {
                var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
                jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");

                var defaultJwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                var customJwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                customJwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
                customJwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
                jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                    new ReactiveJwtGrantedAuthoritiesConverterAdapter(token ->
                        Stream.concat(defaultJwtGrantedAuthoritiesConverter.convert(token).stream(),
                                customJwtGrantedAuthoritiesConverter.convert(token).stream())
                            .toList()));

                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
            }))
            .build();
    }
}
