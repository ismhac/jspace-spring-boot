package com.ismhac.jspace.service.auth.impl;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.dto.employee.EmployeeRegisterRequest;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse employeeRegisterWithEmailPassword(EmployeeRegisterRequest employeeRegisterRequest) {
        return null;
    }
}
