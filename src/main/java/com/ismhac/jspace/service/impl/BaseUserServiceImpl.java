package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.employee.EmployeeDTO;
import com.ismhac.jspace.dto.employee.EmployeeRegisterRequest;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.mapper.EmployeeMapper;
import com.ismhac.jspace.model.BaseUser;
import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.primaryKey.EmployeeID;
import com.ismhac.jspace.repository.BaseUserRepository;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.service.BaseUserService;
import com.ismhac.jspace.util.response.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseUserServiceImpl implements BaseUserService {

    @Value("${init.role.code.employee}")
    private String employeeRoleCode;

    private final PasswordEncoder passwordEncoder;

    private final BaseUserRepository baseUserRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDTO employeeRegisterWithEmailPassword(EmployeeRegisterRequest employeeRegisterRequest) {
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
            EmployeeDTO employeeDTO = EmployeeMapper.INSTANCE.toDTO(savedEmployee);
            return employeeDTO;
        }
    }
}
