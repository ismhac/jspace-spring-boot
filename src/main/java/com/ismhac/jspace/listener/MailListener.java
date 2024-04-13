package com.ismhac.jspace.listener;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.event.SendMailCreateAdminEvent;
import com.ismhac.jspace.event.SendMailForgotPasswordEvent;
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
    public void handleAdminForgotPasswordEvent(SendMailForgotPasswordEvent event) {

        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(event.getAdminForgotPasswordRequest().getEmail())
                .body(event.getAdminForgotPasswordRequest().getBody())
                .subject(event.getAdminForgotPasswordRequest().getSubject())
                .build();

        mailService.sendMail(sendMailRequest);
    }

    @EventListener
    @Async
    public void handleSendMailCreateAdmin(SendMailCreateAdminEvent event){
        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(event.getAdminCreateRequest().getEmail())
                .body(event.getAdminCreateRequest().getBody())
                .subject(event.getAdminCreateRequest().getSubject())
                .build();

        mailService.sendMail(sendMailRequest);
    }
}
