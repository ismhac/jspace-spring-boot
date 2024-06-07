package com.ismhac.jspace.controller.auth;


import com.ismhac.jspace.config.security.oauth2.OAuth2Service;
import com.ismhac.jspace.dto.auth.reponse.AuthenticationResponse;
import com.ismhac.jspace.dto.auth.reponse.IntrospectResponse;
import com.ismhac.jspace.dto.auth.request.IntrospectRequest;
import com.ismhac.jspace.dto.auth.request.LoginRequest;
import com.ismhac.jspace.dto.auth.request.LogoutRequest;
import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.request.SendMailResponse;
import com.ismhac.jspace.dto.role.response.RoleDto;
import com.ismhac.jspace.dto.user.admin.adminForgotPassword.request.AdminForgotPasswordRequest;
import com.ismhac.jspace.dto.user.admin.request.AdminVerifyEmailRequest;
import com.ismhac.jspace.dto.user.admin.response.AdminDto;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.service.common.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;
    OAuth2Service oAuth2Service;

    @GetMapping("/roles")
    public ApiResponse<List<RoleDto>> getRolesForRegister() {
        List<RoleDto> roleDtoList = authService.getRolesForRegister();
        ApiResponse<List<RoleDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleDtoList);
        return apiResponse;
    }

    @PostMapping("/users/login")
    public ApiResponse<AuthenticationResponse<Object>> userLogin(
            @RequestBody Map<String, Object> data) {
//        log.info("data: {}", data);
        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(oAuth2Service.userLogin(data))
                .build();
    }

    @PostMapping("/users/register")
    public ApiResponse<AuthenticationResponse<Object>> userRegister(
            @RequestParam("role") RoleCode roleCode,
            @RequestBody Map<String, Object> data) {
//        log.info("data: {}", data);

        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(oAuth2Service.userRegister(data, roleCode))
                .build();
    }

    @PostMapping("/admin/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<AuthenticationResponse<Object>> adminLogin(
            @RequestBody @Valid LoginRequest loginRequest) {
        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(authService.adminLogin(loginRequest))
                .build();
    }

    @PostMapping("/introspect")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<IntrospectResponse> introspect(
            @RequestBody @Valid IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authService.introspect(introspectRequest))
                .build();
    }

    @PostMapping("/admin-refresh-token")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<AuthenticationResponse<Object>> adminRefreshAccessToken(
            @RequestHeader("refreshToken") String refreshToken) throws ParseException, JOSEException {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new AppException(ErrorCode.MISSING_HEADER_VALUE);
        }
        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(authService.adminRefreshAccessToken(refreshToken))
                .build();
    }

    @PostMapping("/user-refresh-token")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<AuthenticationResponse<Object>> userRefreshAccessToken(
            @RequestHeader("refreshToken") String refreshToken) throws ParseException, JOSEException {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new AppException(ErrorCode.MISSING_HEADER_VALUE);
        }
        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(authService.userRefreshAccessToken(refreshToken))
                .build();
    }

    @PostMapping("/admins/forgot-password")
    public ApiResponse<SendMailResponse> sendMailAdminForgotPassword(
            @RequestBody AdminForgotPasswordRequest adminForgotPasswordRequest) {
        var result = authService.sendMailAdminForgotPassword(adminForgotPasswordRequest);
        return ApiResponse.<SendMailResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @RequestBody LogoutRequest logoutRequest)
            throws ParseException, JOSEException {
        authService.logout(logoutRequest);
        return ApiResponse.<Void>builder()
                .build();
    }


    @GetMapping("/admin/profile")
    public ApiResponse<AdminDto> getAdminInfoFromToken() {
        var result = authService.getAdminInfoFromToken();
        return ApiResponse.<AdminDto>builder()
                .result(result)
                .build();
    }

    @GetMapping("/employee/profile")
    public ApiResponse<EmployeeDto> fetchEmployeeFromToken(){
        var result = authService.fetchEmployeeFromToken();
        return ApiResponse.<EmployeeDto>builder()
                .result(result)
                .build();
    }

    @GetMapping("/candidate/profile")
    public ApiResponse<CandidateDto> fetchCandidateFromToken(){
        var result = authService.fetchCandidateFromToken();
        return ApiResponse.<CandidateDto>builder()
                .result(result)
                .build();
    }

    @PostMapping("/admin-verify-email")
    public ApiResponse<AdminDto> handleVerifyEmail(
            @RequestBody AdminVerifyEmailRequest adminVerifyEmailRequest) {
        var result = authService.handleVerifyEmail(adminVerifyEmailRequest);
        return ApiResponse.<AdminDto>builder()
                .result(result)
                .build();
    }
}
