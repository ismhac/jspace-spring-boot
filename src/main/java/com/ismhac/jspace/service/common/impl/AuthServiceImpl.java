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
import com.ismhac.jspace.dto.user.admin.request.AdminVerifyEmailRequest;
import com.ismhac.jspace.dto.user.admin.response.AdminDto;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.event.ForgotPasswordEvent;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.mapper.*;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.common.AuthService;
import com.ismhac.jspace.util.HashUtils;
import com.ismhac.jspace.util.UserUtils;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    EmployeeRepository employeeRepository;
    CandidateRepository candidateRepository;
    AdminRepository adminRepository;
    AdminForgotPasswordTokenRepository adminForgotPasswordTokenRepository;
    RefreshTokenRepository refreshTokenRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    JwtService jwtService;
    HashUtils hashUtils;
    ApplicationEventPublisher applicationEventPublisher;
    AdminMapper adminMapper;
    AdminRequestVerifyEmailRepository adminRequestVerifyEmailRepository;
    CandidateFollowCompanyRepository candidateFollowCompanyRepository;

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

                ForgotPasswordEvent forgotPasswordEvent = new ForgotPasswordEvent(this, adminForgotPasswordRequest);

                applicationEventPublisher.publishEvent(forgotPasswordEvent);

                sendMailResponse.setEmail(email);
                sendMailResponse.setOtpCreatedDateTime(otpCreatedDateTime);
            } else {
                LocalDateTime otpCreatedDateTime = adminForgotPasswordToken.get().getOtpCreatedDateTime();
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(otpCreatedDateTime, now);

                if (!(duration.compareTo(Duration.ofMinutes(1)) > 0)) {
                    throw new AppException(ErrorCode.TOKEN_EXPIRE);
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

                    ForgotPasswordEvent forgotPasswordEvent = new ForgotPasswordEvent(this, adminForgotPasswordRequest);

                    applicationEventPublisher.publishEvent(forgotPasswordEvent);

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

    @Override
    public AdminDto getAdminInfoFromToken() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = (String) jwt.getClaims().get("sub");

        Admin admin = adminRepository.findAdminByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));

//        log.info("{}", jwt.getClaims());
        return adminMapper.toAdminDto(admin);
    }

    @Override
    @PreAuthorize("hasRole('EMPLOYEE')")
    public EmployeeDto fetchEmployeeFromToken() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("{}", jwt.getClaims());
        log.info("{}", jwt.getClaims().get("email"));

        Map<String, Object> employeeInFo = employeeRepository
                .getInfoByEmail((String) jwt.getClaims().get("email"));

        if (employeeInFo.isEmpty()) throw new AppException(ErrorCode.UNAUTHENTICATED);

        return EmployeeDto.builder()
                .user(UserMapper.instance.toUserDto((User) employeeInFo.get("user")))
                .company(CompanyMapper.instance.eToDto((Company) employeeInFo.get("company"),candidateFollowCompanyRepository))
                .verifiedByCompany((Boolean) employeeInFo.get("verifiedByCompany"))
                .hasFullCredentialInfo((Boolean) employeeInFo.get("hasFullCredentialInfo"))
                .hasCompany((Boolean) employeeInFo.get("hasCompany"))
                .companyEmailVerified((Boolean) employeeInFo.get("companyEmailVerified"))
                .companyVerified((Boolean) employeeInFo.get("companyVerified"))
                .build();
    }

    @Override
    public CandidateDto fetchCandidateFromToken() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Candidate> candidate = candidateRepository
                .findByUserEmail((String) jwt.getClaims().get("email"));

        if(candidate.isEmpty()) throw new AppException(ErrorCode.UNAUTHENTICATED);

        return CandidateMapper.instance.eToDto(candidate.get());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdminDto handleVerifyEmail(AdminVerifyEmailRequest adminVerifyEmailRequest) {
//        Jwt jwt = Jwt.withTokenValue(adminVerifyEmailRequest.getToken()).build();
//
//        String username = (String) jwt.getClaims().get("sub");

        Optional<AdminRequestVerifyEmail> adminRequestVerifyEmail = adminRequestVerifyEmailRepository.findByToken(adminVerifyEmailRequest.getToken());

        if (adminRequestVerifyEmail.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        if (adminRequestVerifyEmail.get().getAdmin().getEmailVerified()) {
            throw new AppException(ErrorCode.EMAIL_HAS_BEEN_VERIFIED);
        }

        LocalDateTime otpCreatedDateTime = adminRequestVerifyEmail.get().getOtpCreatedDateTime();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(now, otpCreatedDateTime);

        if (duration.compareTo(Duration.ofMinutes(10)) > 0) {
            throw new AppException(ErrorCode.TOKEN_EXPIRE);
        } else {
            Admin admin = adminRequestVerifyEmail.get().getAdmin();
            admin.setEmailVerified(true);

            return adminMapper.toAdminDto(adminRepository.save(admin));
        }
    }
}
