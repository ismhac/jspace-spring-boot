package com.ismhac.jspace.listener;

import com.ismhac.jspace.dto.common.SendMailRequest;
import com.ismhac.jspace.event.AdminForgotPasswordEvent;
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
    public void handleAdminForgotPasswordEvent(AdminForgotPasswordEvent event) {

        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(event.getAdminForgotPasswordRequest().getEmail())
                .body(event.getAdminForgotPasswordRequest().getBody())
                .subject(event.getAdminForgotPasswordRequest().getSubject())
                .build();

        mailService.sendMail(sendMailRequest);
    }
}
