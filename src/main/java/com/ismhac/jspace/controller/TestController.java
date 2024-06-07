package com.ismhac.jspace.controller;

import com.ismhac.jspace.config.security.oauth2.OAuth2Service;
import com.ismhac.jspace.dto.auth.reponse.AuthenticationResponse;
import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.role.response.RoleDto;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.service.common.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Hidden
@RestController
@Slf4j
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final AuthService authService;
    private final OAuth2Service oAuth2Service;

    @GetMapping("/roles")
    public ApiResponse<List<RoleDto>> getRolesForRegister() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {}", auth.getName());
        auth.getAuthorities().stream().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        List<RoleDto> roleDtoList = authService.getRolesForRegister();
        ApiResponse<List<RoleDto>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleDtoList);
        return apiResponse;
    }

    @PostMapping("/login_test")
    public ApiResponse<AuthenticationResponse<Object>> testLoginOAuth2(@RequestBody Map<String, Object> data) {
        log.info("data: {}", data);
        return ApiResponse.<AuthenticationResponse<Object>>builder().result(oAuth2Service.userLogin(data)).build();
    }

    @PostMapping("/register_test")
    public ApiResponse<AuthenticationResponse<Object>> testRegister(@RequestParam("role") RoleCode roleCode, @RequestBody Map<String, Object> data) {
        return ApiResponse.<AuthenticationResponse<Object>>builder().result(oAuth2Service.userRegister(data, roleCode)).build();
    }

    @GetMapping("test/getPrincipal")
    public Object test() {
        log.info("{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaims();
    }
}
