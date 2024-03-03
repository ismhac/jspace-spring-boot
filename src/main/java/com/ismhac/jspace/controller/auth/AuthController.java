package com.ismhac.jspace.controller.auth;

import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.dto.employee.EmployeeRegisterRequest;
import com.ismhac.jspace.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/employees/register")
    public ResponseEntity<AuthenticationResponse> employeeRegisterWithEmailPassword(
            @RequestBody EmployeeRegisterRequest employeeRegisterRequest
    ){
        AuthenticationResponse authenticationResponse = authService
                .employeeRegisterWithEmailPassword(employeeRegisterRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
