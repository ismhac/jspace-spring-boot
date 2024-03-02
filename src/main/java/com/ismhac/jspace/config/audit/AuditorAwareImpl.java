package com.ismhac.jspace.config.audit;

import com.ismhac.jspace.model.BaseUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNullApi;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return Optional.empty();
        }
        BaseUser baseUser = (BaseUser) principal;
        return Optional.of(baseUser.getId());
    }
}
