package com.ismhac.jspace.listener;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.event.CreateAdminEvent;
import com.ismhac.jspace.event.ForgotPasswordEvent;
import com.ismhac.jspace.event.RequestCompanyToVerifyForEmployeeEvent;
import com.ismhac.jspace.event.RequestCompanyVerifyEmailEvent;
import com.ismhac.jspace.service.common.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailListener {
    private final MailService mailService;
    @EventListener
    @Async
    public void handleAdminForgotPasswordEvent(ForgotPasswordEvent event) {

        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(event.getAdminForgotPasswordRequest().getEmail())
                .body(event.getAdminForgotPasswordRequest().getBody())
                .subject(event.getAdminForgotPasswordRequest().getSubject())
                .build();

        mailService.sendMail(sendMailRequest);
    }

    @EventListener
    @Async
    public void handleSendMailCreateAdmin(CreateAdminEvent event){
        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(event.getAdminCreateRequest().getEmail())
                .body(event.getAdminCreateRequest().getBody())
                .subject(event.getAdminCreateRequest().getSubject())
                .build();

        mailService.sendMailWithTemplate(sendMailRequest);
    }

    @EventListener
    @Async
    public void requestCompanyVerifyEmail(RequestCompanyVerifyEmailEvent event){
        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(event.getSendMailRequest().getEmail())
                .body(event.getSendMailRequest().getBody())
                .subject(event.getSendMailRequest().getSubject())
                .build();
        mailService.sendMail(sendMailRequest);
    }

    @EventListener
    @Async
    public void requestCompanyToVerifyForEmployee(RequestCompanyToVerifyForEmployeeEvent event){
        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(event.getSendMailRequest().getEmail())
                .body(event.getSendMailRequest().getBody())
                .subject(event.getSendMailRequest().getSubject())
                .build();
        mailService.sendMail(sendMailRequest);
    }
}
