package com.configserverllp.configassets.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {
    // Enables automatic population of createdAt, updatedAt, createdBy, updatedBy
}
