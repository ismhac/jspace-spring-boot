package com.ismhac.jspace.listener;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.event.*;
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
        mailService.sendMail(SendMailRequest.builder()
                .email(event.getAdminForgotPasswordRequest().getEmail())
                .body(event.getAdminForgotPasswordRequest().getBody())
                .subject(event.getAdminForgotPasswordRequest().getSubject())
                .build());
    }


    @EventListener
    @Async
    public void handleAdminSendBillEvent(SendBillEvent event) {
        mailService.sendMail(SendMailRequest.builder()
                .email(event.getSendMailRequest().getEmail())
                .body(event.getSendMailRequest().getBody())
                .subject(event.getSendMailRequest().getSubject())
                .build());
    }


    @EventListener
    @Async
    public void handleSendMailCreateAdmin(CreateAdminEvent event) {
        mailService.sendMailWithTemplate(SendMailRequest.builder()
                .email(event.getAdminCreateRequest().getEmail())
                .body(event.getAdminCreateRequest().getBody())
                .subject(event.getAdminCreateRequest().getSubject())
                .build());
    }

    @EventListener
    @Async
    public void requestCompanyVerifyEmail(RequestCompanyVerifyEmailEvent event) {
        mailService.sendMail(SendMailRequest.builder()
                .email(event.getSendMailRequest().getEmail())
                .body(event.getSendMailRequest().getBody())
                .subject(event.getSendMailRequest().getSubject())
                .build());
    }

    @EventListener
    @Async
    public void requestCompanyToVerifyForEmployee(RequestCompanyToVerifyForEmployeeEvent event) {
        mailService.sendMail(SendMailRequest.builder()
                .email(event.getSendMailRequest().getEmail())
                .body(event.getSendMailRequest().getBody())
                .subject(event.getSendMailRequest().getSubject())
                .build());
    }

    @EventListener
    @Async
    public void suggestJobs(SuggestJobs event) {
        mailService.sendMail(SendMailRequest.builder()
                .email(event.getSendMailRequest().getEmail())
                .body(event.getSendMailRequest().getBody())
                .subject(event.getSendMailRequest().getSubject())
                .build());
    }
}
