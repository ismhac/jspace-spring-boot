package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.auth.reponse.AuthenticationResponse;
import com.ismhac.jspace.dto.auth.reponse.IntrospectResponse;
import com.ismhac.jspace.dto.auth.request.*;
import com.ismhac.jspace.dto.common.request.SendMailResponse;
import com.ismhac.jspace.dto.role.response.RoleDto;
import com.ismhac.jspace.dto.user.admin.adminForgotPassword.request.AdminForgotPasswordRequest;
import com.ismhac.jspace.dto.user.admin.request.AdminVerifyEmailRequest;
import com.ismhac.jspace.dto.user.admin.response.AdminDto;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;

public interface AuthService {
    List<RoleDto> getRolesForRegister();

    AdminDto getAdminInfoFromToken();

    EmployeeDto fetchEmployeeFromToken();

    CandidateDto fetchCandidateFromToken();

    AuthenticationResponse<Object> adminLogin(LoginRequest loginRequest);

    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException;

    AuthenticationResponse<Object> adminRefreshAccessToken(String token) throws ParseException, JOSEException;

    AuthenticationResponse<Object> userRefreshAccessToken(String token) throws ParseException, JOSEException;

    void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;

    SendMailResponse sendMailAdminForgotPassword(AdminForgotPasswordRequest adminForgotPasswordRequest);

    AdminDto handleVerifyEmail(AdminVerifyEmailRequest adminVerifyEmailRequest);

    AuthenticationResponse<Object> registerWithEmailAndPassword(RegisterEmailPasswordRequest request);

    AuthenticationResponse<Object> loginWithEmailPassword(LoginEmailPasswordRequest request);
}
