package com.ismhac.jspace.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (jwt == null) {
            return Optional.empty();
        }
        if (jwt.getClaims().get("id") != null) {
            return Optional.of(((Integer) jwt.getClaims().get("id")));
        }
        return Optional.empty();
    }
}
