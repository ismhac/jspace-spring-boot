package com.ismhac.jspace.config.security.oauth2;

import com.ismhac.jspace.config.security.jwt.JwtService;
import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.enums.RoleCode;
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

    public AuthenticationResponse<Object> userRegister(Map<String, Object> data, RoleCode roleCode) {

        String email = (String) data.get("email");

        Role roleCheck = roleRepository.findRoleByCode(roleCode)
                .orElseThrow(()-> new AppException(ErrorCode.INVALID_TOKEN));

        // check user is existed
        User user = userRepository.findUserByEmail(email).orElseGet(() -> {


            User newUser = User.builder()
                    .role(roleCheck)
                    .activated(true)
                    .email(email)
                    .build();

            User savedUser = userRepository.save(newUser);
            return savedUser;
        });

        var accessToken = jwtService.generateUserToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
