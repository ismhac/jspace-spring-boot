package com.ismhac.jspace.controller.auth;


import com.ismhac.jspace.dto.auth.*;
import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.service.common.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    /* */
    @GetMapping("/roles")
    public ApiResponse<List<RoleDto>> getRolesForRegister() {
        List<RoleDto> roleDtoList = authService.getRolesForRegister();
        ApiResponse<List<RoleDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleDtoList);
        return apiResponse;
    }


    /* */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDto> register(
            @Schema(name = "role", allowableValues = {"EMPLOYEE", "CANDIDATE"})
            @RequestParam("role") @Valid String roleCode,
            
            @RequestBody @Valid UserRegisterRequest registerRequest) {
        UserDto userDto = authService.register(roleCode, registerRequest);
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userDto);
        return apiResponse;
    }

    /* */
    @PostMapping("/authentication")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<AuthenticationResponse<Object>> authentication(@RequestBody @Valid LoginRequest loginRequest) {
        return ApiResponse.<AuthenticationResponse<Object>>builder()
                .result(authService.authentication(loginRequest))
                .build();
    }

    /* */
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

    @GetMapping("/api/v1/auth/login/oauth2")
    public void redirectToGoogleLogin(@RequestParam String myParam, HttpServletResponse response) throws IOException {
        String state = myParam;
        String redirectUrl = "/oauth2/authorization/google?state=" + state;
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/login/oauth2/callback")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OAuth2User handleGoogleCallback(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            HttpSession session) {

        String role = (String) session.getAttribute("role");
        log.info("{}", oAuth2User);
        return oAuth2User;
    }
}
