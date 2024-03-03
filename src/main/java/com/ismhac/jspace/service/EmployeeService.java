package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.employee.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO registerWithEmailPassword(String email, String password);
}
