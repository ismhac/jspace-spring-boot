package com.ismhac.jspace.event;

import com.ismhac.jspace.dto.user.admin.adminForgotPassword.request.AdminForgotPasswordRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class ForgotPasswordEvent extends ApplicationEvent {

    private AdminForgotPasswordRequest adminForgotPasswordRequest;
    public ForgotPasswordEvent(Object source, AdminForgotPasswordRequest adminForgotPasswordRequest) {
        super(source);
        this.adminForgotPasswordRequest = adminForgotPasswordRequest;
    }
}
