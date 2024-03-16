package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.auth.*;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.model.enums.RoleCode;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface AuthService {
    List<RoleDto> getRolesForRegister();

    UserDto register(String roleCode, UserRegisterRequest registerRequest);

    AuthenticationResponse<Object> authentication(LoginRequest loginRequest);

    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException;

    AuthenticationResponse<Object> refreshAccessToken(String token) throws ParseException, JOSEException;
}
