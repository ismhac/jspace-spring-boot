package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.auth.reponse.AuthenticationResponse;
import com.ismhac.jspace.dto.auth.reponse.IntrospectResponse;
import com.ismhac.jspace.dto.auth.request.IntrospectRequest;
import com.ismhac.jspace.dto.auth.request.LoginRequest;
import com.ismhac.jspace.dto.auth.request.LogoutRequest;
import com.ismhac.jspace.dto.common.request.SendMailResponse;
import com.ismhac.jspace.dto.role.response.RoleDto;
import com.ismhac.jspace.dto.user.admin.adminForgotPassword.request.AdminForgotPasswordRequest;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;

public interface AuthService {
    List<RoleDto> getRolesForRegister();

//    UserDto register(String roleCode, UserRegisterRequest registerRequest);

    AuthenticationResponse<Object> adminLogin(LoginRequest loginRequest);

    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException;

    AuthenticationResponse<Object> adminRefreshAccessToken(String token) throws ParseException, JOSEException;

    AuthenticationResponse<Object> userRefreshAccessToken(String token) throws ParseException, JOSEException;

    void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;

    SendMailResponse sendMailAdminForgotPassword(AdminForgotPasswordRequest adminForgotPasswordRequest);
}
