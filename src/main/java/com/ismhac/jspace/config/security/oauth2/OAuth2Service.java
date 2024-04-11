package com.ismhac.jspace.config.security.oauth2;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.exception.ErrorCode;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2Service {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private  final RoleRepository roleRepository;
    
    private final EmployeeRepository employeeRepository;
    
    private final CandidateRepository candidateRepository;

    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse<Object> userLogin(Map<String, Object> data) {

        String email = (String) data.get("email");

        // check user is existed
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            var accessToken = jwtService.generateUserToken(user.get());
            var refreshToken = jwtService.generateRefreshToken(user.get());
            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            return AuthenticationResponse.builder()
                    .accessToken("")
                    .refreshToken("")
                    .build();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse<Object> userRegister(Map<String, Object> data, RoleCode roleCode) {

        String email = (String) data.get("email");

        Role roleCheck = roleRepository.findRoleByCode(roleCode)
                .orElseThrow(()-> new AppException(ErrorCode.INVALID_TOKEN));

        Optional<User> user = userRepository.findUserByEmail(email);

        if(user.isPresent()){
            throw new BadRequestException(ErrorCode.USER_EXISTED);
        }else {
            User newUser = User.builder()
                    .role(roleCheck)
                    .activated(true)
                    .email(email)
                    .build();

            User savedUser = userRepository.save(newUser);

            if(roleCheck.getCode().equals(RoleCode.EMPLOYEE)){
                Employee employee = Employee.builder()
                        .id(EmployeeId.builder()
                                .user(savedUser)
                                .build())
                        .build();
                
                employeeRepository.save(employee);
            } else if (roleCheck.getCode().equals(RoleCode.CANDIDATE)) {
                Candidate candidate = Candidate.builder()
                        .id(CandidateId.builder()
                                .user(savedUser)
                                .build())
                        .build();

                candidateRepository.save(candidate);
            } else {
                throw new BadRequestException(ErrorCode.INVALID_ROLE_REGISTER);
            }

            var accessToken = jwtService.generateUserToken(savedUser);
            var refreshToken = jwtService.generateRefreshToken(savedUser);
            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }
}
