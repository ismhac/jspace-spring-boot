package com.ismhac.jspace.util;

import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public User getUserFromToken() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = (String) jwt.getClaims().get("sub");

        User user = userRepository
                .findUserByUsernameOrEmail(
                        (String) jwt.getClaims().get("sub"),
                        (String) jwt.getClaims().get("email"))
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        return user;
    }
}
