package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final AuthService authService;

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
}
