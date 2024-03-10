package com.ismhac.jspace.controller.auth;

import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.dto.auth.LoginRequest;
import com.ismhac.jspace.dto.auth.LoginResponse;
import com.ismhac.jspace.dto.auth.UserRegisterRequest;
import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.service.common.AuthService;
import com.ismhac.jspace.service.common.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;


    @GetMapping("/roles")
    public ApiResponse<List<RoleDto>> getRolesForRegister(){
        List<RoleDto> roleDtoList = authService.getRolesForRegister();
        ApiResponse<List<RoleDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleDtoList);
        return apiResponse;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDto> register(
            @RequestParam("role") @Valid String roleCode,
            @RequestBody @Valid UserRegisterRequest registerRequest){
        UserDto userDto = authService.register(roleCode, registerRequest);
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userDto);
        return apiResponse;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        return ApiResponse.<LoginResponse>builder()
                .result(authService.login(loginRequest))
                .build();
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<AuthenticationResponse> refreshAccessToken(
            @RequestHeader("refresh_token") String refreshToken){
        if(refreshToken == null || refreshToken.isBlank()){
            throw new BadRequestException(ErrorCode.MISSING_HEADER_VALUE);
        }
        return ApiResponse.<AuthenticationResponse>builder()
                .result(tokenService.refreshAccessToken(refreshToken))
                .build();
    }
}
