package com.ismhac.jspace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

//    private final BaseUserService baseUserService;
//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<EmployeeDTO> registerWithEmailPassword(
//            @RequestBody EmployeeRegisterRequest employeeRegisterRequest) {
//        EmployeeDTO employeeDTO = baseUserService.employeeRegisterWithEmailPassword(employeeRegisterRequest);
//        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
//    }
}
