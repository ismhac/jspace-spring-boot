package com.ismhac.jspace.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {
    public PersistenceConfig() {
        super();
    }

    @Bean("auditorProvider")
    public AuditorAware<Integer> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
