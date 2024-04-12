package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.reponse.AuthenticationResponse;
import com.ismhac.jspace.dto.auth.reponse.IntrospectResponse;
import com.ismhac.jspace.dto.auth.request.IntrospectRequest;
import com.ismhac.jspace.dto.auth.request.LoginRequest;
import com.ismhac.jspace.dto.auth.request.LogoutRequest;
import com.ismhac.jspace.dto.common.request.SendMailResponse;
import com.ismhac.jspace.dto.role.response.RoleDto;
import com.ismhac.jspace.dto.user.admin.adminForgotPassword.request.AdminForgotPasswordRequest;
import com.ismhac.jspace.event.AdminForgotPasswordEvent;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.mapper.RoleMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.common.AuthService;
import com.ismhac.jspace.util.HashUtils;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    RoleMapper roleMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserRepository userRepository;

    AdminRepository adminRepository;

    AdminForgotPasswordTokenRepository adminForgotPasswordTokenRepository;

    RefreshTokenRepository refreshTokenRepository;

    InvalidatedTokenRepository invalidatedTokenRepository;

    JwtService jwtService;

    HashUtils hashUtils;

    ApplicationEventPublisher applicationEventPublisher;

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
    public AuthenticationResponse<Object> adminRefreshAccessToken(String token) throws ParseException, JOSEException {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(hashUtils.hmacSHA512(token.trim()))
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));
        if (!jwtService.introspect(token)) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateAdminToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    public AuthenticationResponse<Object> userRefreshAccessToken(String token) throws ParseException, JOSEException {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(hashUtils.hmacSHA512(token.trim()))
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));
        if (!jwtService.introspect(token)) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateUserToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SendMailResponse sendMailAdminForgotPassword(AdminForgotPasswordRequest adminForgotPasswordRequest) {
        SendMailResponse sendMailResponse = new SendMailResponse();
        try {
            String email = adminForgotPasswordRequest.getEmail().trim();

            Admin admin = adminRepository.findAdminByAdminTypeAndEmail(AdminType.BASIC, email)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

            Optional<AdminForgotPasswordToken> adminForgotPasswordToken = adminForgotPasswordTokenRepository
                    .findLatestByAdminId(admin.getId().getUser().getId());

            if (adminForgotPasswordToken.isEmpty()) {

                LocalDateTime otpCreatedDateTime = LocalDateTime.now();

                String token = jwtService.generateAdminForgotPasswordToken(admin);

                AdminForgotPasswordToken newAdminForgotPasswordToken = AdminForgotPasswordToken.builder()
                        .admin(admin)
                        .otpCreatedDateTime(otpCreatedDateTime)
                        .token(token)
                        .build();

                adminForgotPasswordTokenRepository.save(newAdminForgotPasswordToken);

                String body = adminForgotPasswordRequest.getReturnUrl().concat(String.format("?token=%s", token));

                adminForgotPasswordRequest.setBody(body);

                AdminForgotPasswordEvent adminForgotPasswordEvent = new AdminForgotPasswordEvent(this, adminForgotPasswordRequest);

                applicationEventPublisher.publishEvent(adminForgotPasswordEvent);

                sendMailResponse.setEmail(email);
                sendMailResponse.setOtpCreatedDateTime(otpCreatedDateTime);
            } else {
                LocalDateTime otpCreatedDateTime = adminForgotPasswordToken.get().getOtpCreatedDateTime();
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(otpCreatedDateTime, now);

                if (!(duration.compareTo(Duration.ofMinutes(1)) > 0)) {
                    throw new AppException(ErrorCode.INVALID_TOKEN);
                } else {
                    String token = jwtService.generateAdminForgotPasswordToken(admin);

                    AdminForgotPasswordToken newAdminForgotPasswordToken = AdminForgotPasswordToken.builder()
                            .admin(admin)
                            .otpCreatedDateTime(otpCreatedDateTime)
                            .token(token)
                            .build();
                    adminForgotPasswordTokenRepository.save(newAdminForgotPasswordToken);

                    String body = adminForgotPasswordRequest.getReturnUrl().concat(String.format("?token=%s", token));

                    adminForgotPasswordRequest.setBody(body);

                    AdminForgotPasswordEvent adminForgotPasswordEvent = new AdminForgotPasswordEvent(this, adminForgotPasswordRequest);

                    applicationEventPublisher.publishEvent(adminForgotPasswordEvent);

                    sendMailResponse.setEmail(email);
                    sendMailResponse.setOtpCreatedDateTime(otpCreatedDateTime);
                }
            }
            return sendMailResponse;
        } catch (Exception e) {
            return sendMailResponse;
        }
    }


    @Override
    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {
        var signToken = jwtService.verifyToken(logoutRequest.getToken());

        String jti = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }
}
