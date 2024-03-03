package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.employee.EmployeeDTO;
import com.ismhac.jspace.dto.employee.EmployeeRegisterRequest;
import com.ismhac.jspace.service.BaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final BaseUserService baseUserService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeDTO> registerWithEmailPassword(
            @RequestBody EmployeeRegisterRequest employeeRegisterRequest) {
        EmployeeDTO employeeDTO = baseUserService.employeeRegisterWithEmailPassword(employeeRegisterRequest);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }
}
