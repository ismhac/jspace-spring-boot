package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.*;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.exception.*;
import com.ismhac.jspace.mapper.RoleMapper;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.model.primaryKey.CandidateId;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.common.AuthService;
import com.ismhac.jspace.util.HashUtils;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;

    private final HashUtils hashUtils;

    /* */
    @Override
    public List<RoleDto> getRolesForRegister() {
        List<Role> roleList = roleRepository.findNonAdminRoles(RoleCode.SUPER_ADMIN, RoleCode.ADMIN);

        return roleMapper.toRoleDtoList(roleList);
    }

    /* */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto register(String roleCode, UserRegisterRequest registerRequest) {
        boolean userExisted = userRepository.existsByUsername(registerRequest.getUsername());
        if (userExisted) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (!roleCode.equals(RoleCode.EMPLOYEE.toString()) && !roleCode.equals(RoleCode.CANDIDATE.toString())) {
            throw new AppException(ErrorCode.INVALID_ROLE_REGISTER);
        } else {
            if (RoleCode.EMPLOYEE.getName().equalsIgnoreCase(roleCode)) {
                return saveEmployee(registerRequest);
            } else {
                return saveCandidate(registerRequest);
            }
        }
    }

    /* */
    @Override
    public AuthenticationResponse<Object> authentication(LoginRequest loginRequest) {
        var user = userRepository.findUserByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /* */
    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {
        boolean valid = jwtService.introspect(introspectRequest.getToken());
        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    /* */
    @Override
    public AuthenticationResponse<Object> refreshAccessToken(String token)
            throws ParseException, JOSEException {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(hashUtils.hmacSHA512(token.trim()))
                .orElseThrow(()-> new AppException(ErrorCode.INVALID_TOKEN));
        if(!jwtService.introspect(token)){
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        }
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    /* */
    @Transactional(rollbackFor = Exception.class)
    protected UserDto saveEmployee(UserRegisterRequest registerRequest) {
        Role role = roleRepository.findRoleByCode(RoleCode.EMPLOYEE)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ROLE));

        User user = User.builder()
                .username(registerRequest.getUsername())
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

    /* */
    @Transactional(rollbackFor = Exception.class)
    protected UserDto saveCandidate(UserRegisterRequest registerRequest) {
        Role role = roleRepository.findRoleByCode(RoleCode.CANDIDATE)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ROLE));

        User user = User.builder()
                .username(registerRequest.getUsername())
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
