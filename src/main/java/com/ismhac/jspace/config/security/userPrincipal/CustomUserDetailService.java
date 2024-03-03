package com.ismhac.jspace.config.security.userPrincipal;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailService {
    UserDetails loadUserByEmail(String email);
}
