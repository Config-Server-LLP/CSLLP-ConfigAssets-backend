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
                        // Public endpoints
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/login/**", "/oauth2/**").permitAll()
                        // Protect all API routes (require authentication)
                        .pathMatchers("/api/**").authenticated()
                        // Deny everything else
                        .anyExchange().denyAll()
                )

                // ✅ Keycloak OAuth2 Configuration (new style)
                .oauth2Login(oauth2 -> { })   // no redirect config in WebFlux
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter())
                                )
                        )
                );

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
