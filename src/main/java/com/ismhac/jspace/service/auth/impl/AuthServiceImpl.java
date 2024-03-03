package com.ismhac.jspace.service.auth.impl;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.dto.employee.EmployeeRegisterRequest;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.model.BaseUser;
import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.primaryKey.EmployeeID;
import com.ismhac.jspace.repository.BaseUserRepository;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.service.auth.AuthService;
import com.ismhac.jspace.util.response.Status;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Value("${init.role.code.employee}")
    private String employeeRoleCode;

    private final PasswordEncoder passwordEncoder;

    private final BaseUserRepository baseUserRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse employeeRegisterWithEmailPassword(EmployeeRegisterRequest employeeRegisterRequest) {
        Optional<BaseUser> baseUser = baseUserRepository
                .findByEmailAndRoleCode(employeeRegisterRequest.getEmail(), employeeRoleCode);

        if(baseUser.isPresent()){
            throw new BadRequestException(Status.EMPLOYEE_EXIST_EMAIL);
        }else {

            Role role = roleRepository.getRoleByCode(employeeRoleCode);

            BaseUser newBaseUser = BaseUser.builder()
                    .email(employeeRegisterRequest.getEmail())
                    .password(passwordEncoder.encode(employeeRegisterRequest.getPassword()))
                    .role(role)
                    .activated(true)
                    .build();

            BaseUser savedBaseUser = baseUserRepository.save(newBaseUser);

            EmployeeID id = EmployeeID.builder()
                    .baseUser(savedBaseUser)
                    .build();

            Employee employee = Employee.builder()
                    .id(id)
                    .build();

            Employee savedEmployee = employeeRepository.save(employee);


            var jwtToken = jwtService.generateToken(savedBaseUser);
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
            return authenticationResponse;
        }
    }
}
