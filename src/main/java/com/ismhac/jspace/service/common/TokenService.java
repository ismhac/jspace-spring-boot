package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    AuthenticationResponse refreshAccessToken(String token);
}
