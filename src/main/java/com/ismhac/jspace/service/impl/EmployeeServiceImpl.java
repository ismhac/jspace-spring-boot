package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
}
