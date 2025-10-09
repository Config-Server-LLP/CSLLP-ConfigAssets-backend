package com.configserverllp.configassets.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;

@Configuration
@EnableWebFluxSecurity   // Required for Spring Cloud Gateway (WebFlux based)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                // Disable CSRF (not needed for APIs)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Authorize routes
                .authorizeExchange(exchange -> exchange
                        // Allow all API routes for testing (no authentication required)
                        .pathMatchers("/api/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/login/**", "/oauth2/**").permitAll()
                        // Allow everything else
                        .anyExchange().permitAll()
                );

                // Disable OAuth2 for testing
                // .oauth2Login(oauth2 -> { })
                // .oauth2ResourceServer(oauth2 -> oauth2
                //         .jwt(jwt -> jwt
                //                 .jwtAuthenticationConverter(
                //                         new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter())
                //                 )
                //         )
                // );

        return http.build();
    }

    // ✅ JWT Authentication Converter for Keycloak
    private org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter jwtAuthenticationConverter() {
        org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter converter =
                new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter();

        // Extract roles from Keycloak JWT token
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            java.util.Collection<String> roles = jwt.getClaimAsStringList("realm_access.roles");
            if (roles == null) {
                roles = java.util.Collections.emptyList();
            }
            return roles.stream()
                    .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role))
                    .collect(java.util.stream.Collectors.toList());
        });

        return converter;
    }
}
