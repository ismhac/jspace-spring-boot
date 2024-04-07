package com.ismhac.jspace.controller.auth;


import com.ismhac.jspace.config.security.oauth2.OAuth2Service;
import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.dto.auth.IntrospectRequest;
import com.ismhac.jspace.dto.auth.IntrospectResponse;
import com.ismhac.jspace.dto.auth.LoginRequest;
import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.dto.user.admin.adminForgotPassword.AdminForgotPasswordRequest;
import com.ismhac.jspace.exception.BadRequestException;
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
import org.springframework.http.ResponseEntity;
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

    /* */
    @GetMapping("/roles")
    public ApiResponse<List<RoleDto>> getRolesForRegister() {
        List<RoleDto> roleDtoList = authService.getRolesForRegister();
        ApiResponse<List<RoleDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleDtoList);
        return apiResponse;
    }

    /* USER AUTHENTICATION */
    @PostMapping("/users/login")
    public ApiResponse<AuthenticationResponse<Object>> userLogin(@RequestBody Map<String, Object> data) {
//        log.info("data: {}", data);
        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(oAuth2Service.userLogin(data))
                .build();
    }

    @PostMapping("/users/register")
    public ApiResponse<AuthenticationResponse<Object>> userRegister(@RequestParam("role") RoleCode roleCode, @RequestBody Map<String, Object> data) {
//        log.info("data: {}", data);

        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(oAuth2Service.userRegister(data, roleCode))
                .build();
    }

    /* ADMIN AUTHENTICATION */
    @PostMapping("/admin/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<AuthenticationResponse<Object>> adminLogin(@RequestBody @Valid LoginRequest loginRequest) {
        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(authService.adminLogin(loginRequest))
                .build();
    }

    /* TOKEN */
    @PostMapping("/introspect")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<IntrospectResponse> introspect(@RequestBody @Valid IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authService.introspect(introspectRequest))
                .build();
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<AuthenticationResponse<Object>> refreshAccessToken(
            @RequestHeader("refresh_token") String refreshToken)
            throws ParseException, JOSEException {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BadRequestException(ErrorCode.MISSING_HEADER_VALUE);
        }
        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(authService.refreshAccessToken(refreshToken))
                .build();
    }
    /* */

    @PostMapping("/admins/forgot-password")
    public ResponseEntity<Void> sendMailAdminForgotPassword(
            @RequestBody AdminForgotPasswordRequest adminForgotPasswordRequest){
        authService.sendMailAdminForgotPassword(adminForgotPasswordRequest);
        return null;
    }
}
