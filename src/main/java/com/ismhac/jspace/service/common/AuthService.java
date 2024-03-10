package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.auth.LoginRequest;
import com.ismhac.jspace.dto.auth.LoginResponse;
import com.ismhac.jspace.dto.auth.UserRegisterRequest;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.model.enums.RoleCode;

import java.util.List;

public interface AuthService {
    List<RoleDto> getRolesForRegister();

    UserDto register(String roleCode, UserRegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
