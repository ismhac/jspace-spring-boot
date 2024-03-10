package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.model.RefreshToken;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.repository.RefreshTokenRepository;
import com.ismhac.jspace.service.common.TokenService;
import com.ismhac.jspace.util.HashUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final HashUtils hashUtils;

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return jwtService.generateToken(userDetails);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        String token = jwtService.generateRefreshToken();
        saveRefreshToken(((User) userDetails).getId(), token);
        return token;
    }

    @Override
    public AuthenticationResponse refreshAccessToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(hashUtils.hmacSHA512(token.trim()))
                .orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));
        if(!jwtService.isValidRefreshToken(token)){
            throw new BadRequestException(ErrorCode.REFRESH_TOKEN_EXPIRE);
        }
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken((UserDetails) user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private void saveRefreshToken(int userId, String token){
        User user = User.builder()
                .id(userId)
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(hashUtils.hmacSHA512(token.trim()))
                .build();

        refreshTokenRepository.save(refreshToken);
    }
}
