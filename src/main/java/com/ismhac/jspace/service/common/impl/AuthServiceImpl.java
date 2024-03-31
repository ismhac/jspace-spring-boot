package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.*;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.exception.*;
import com.ismhac.jspace.mapper.RoleMapper;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.model.primaryKey.CandidateId;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.common.AuthService;
import com.ismhac.jspace.util.HashUtils;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    RoleMapper roleMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserRepository userRepository;

    RefreshTokenRepository refreshTokenRepository;

    JwtService jwtService;

    HashUtils hashUtils;

    /* */
    @Override
    public List<RoleDto> getRolesForRegister() {
        List<Role> roleList = roleRepository.findNonAdminRoles(RoleCode.SUPER_ADMIN, RoleCode.ADMIN);

        return roleMapper.toRoleDtoList(roleList);
    }

    
    /* */
    @Override
    public AuthenticationResponse<Object> adminLogin(LoginRequest loginRequest) {
        var user = userRepository.findUserByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var accessToken = jwtService.generateAdminToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /* */
    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {
        boolean valid = jwtService.introspect(introspectRequest.getToken());
        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    /* */
    @Override
    public AuthenticationResponse<Object> refreshAccessToken(String token)
            throws ParseException, JOSEException {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(hashUtils.hmacSHA512(token.trim()))
                .orElseThrow(()-> new AppException(ErrorCode.INVALID_TOKEN));
        if(!jwtService.introspect(token)){
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        }
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateAdminToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
