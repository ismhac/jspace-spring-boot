package com.ismhac.jspace.service.auth;

import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.dto.employee.EmployeeRegisterRequest;

public interface AuthService {
    AuthenticationResponse employeeRegisterWithEmailPassword(EmployeeRegisterRequest employeeRegisterRequest);
}
