package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.AuthenticationResponse;
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
}
