package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.dto.auth.LoginRequest;
import com.ismhac.jspace.dto.auth.LoginResponse;
import com.ismhac.jspace.dto.auth.UserRegisterRequest;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.mapper.RoleMapper;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.model.primaryKey.CandidateId;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import com.ismhac.jspace.repository.CandidateRepository;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.repository.RoleRepository;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.common.AuthService;
import com.ismhac.jspace.service.common.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final CandidateRepository candidateRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public List<RoleDto> getRolesForRegister() {
        List<Role> roleList = roleRepository.findNonAdminRoles(RoleCode.SUPER_ADMIN, RoleCode.ADMIN);

        return roleMapper.toRoleDtoList(roleList);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = tokenService.generateAccessToken(userDetails);
        String refreshToken = tokenService.generateRefreshToken(userDetails);

        log.info(String.format("User %s login success", userDetails.getUsername()));
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserDto register(String roleCode, UserRegisterRequest registerRequest) {
        boolean userExisted = userRepository.existsByEmail(registerRequest.getEmail());
        if (userExisted) {
            throw new BadRequestException(ErrorCode.USER_EXISTED);
        }

        if (!roleCode.equals(RoleCode.EMPLOYEE.toString()) && !roleCode.equals(RoleCode.CANDIDATE.toString())) {
            throw new BadRequestException(ErrorCode.INVALID_ROLE_REGISTER);
        } else {
            if (RoleCode.EMPLOYEE.equals(roleCode)) {
                return saveEmployee(registerRequest);
            } else {
                return saveCandidate(registerRequest);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    protected UserDto saveEmployee(UserRegisterRequest registerRequest) {
        Role role = roleRepository.findRoleByCode(RoleCode.EMPLOYEE)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ROLE));

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .activated(true)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        EmployeeId employeeId = EmployeeId.builder()
                .user(savedUser)
                .build();

        Employee employee = Employee.builder()
                .id(employeeId)
                .build();

        employeeRepository.save(employee);

        return userMapper.toUserDto(savedUser);
    }

    @Transactional(rollbackFor = Exception.class)
    protected UserDto saveCandidate(UserRegisterRequest registerRequest) {
        Role role = roleRepository.findRoleByCode(RoleCode.CANDIDATE)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ROLE));

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .activated(true)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        CandidateId candidateId = CandidateId.builder()
                .user(savedUser)
                .build();

        Candidate candidate = Candidate.builder()
                .id(candidateId)
                .build();

        candidateRepository.save(candidate);

        return userMapper.toUserDto(savedUser);
    }
}
