package com.ismhac.jspace.event;

import com.ismhac.jspace.dto.user.admin.adminForgotPassword.request.AdminForgotPasswordRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class SendMailForgotPasswordEvent extends ApplicationEvent {

    private AdminForgotPasswordRequest adminForgotPasswordRequest;
    public SendMailForgotPasswordEvent(Object source, AdminForgotPasswordRequest adminForgotPasswordRequest) {
        super(source);
        this.adminForgotPasswordRequest = adminForgotPasswordRequest;
    }
}
