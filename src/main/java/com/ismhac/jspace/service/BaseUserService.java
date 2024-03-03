package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.employee.EmployeeDTO;
import com.ismhac.jspace.dto.employee.EmployeeRegisterRequest;

public interface BaseUserService {
    EmployeeDTO employeeRegisterWithEmailPassword(EmployeeRegisterRequest employeeRegisterRequest);
}
