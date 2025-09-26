package com.configserverllp.configassets.userservice.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Provides current auditor (user) for JPA auditing fields like createdBy, updatedBy.
 * Integrates with Keycloak or can use a system default for testing.
 */
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO: Replace with Keycloak Principal
        // For now, returning a default user
        return Optional.of("system_user");
    }
}
