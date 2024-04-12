package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.service.common.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Configuration
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(SendMailRequest sendMailRequest) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("hackiddev@gmail.com");
            message.setTo(sendMailRequest.getEmail());
            message.setText(sendMailRequest.getBody());
            message.setSubject(sendMailRequest.getSubject());
            javaMailSender.send(message);
            log.info("Send mail to {} successfully!", sendMailRequest.getEmail());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Send mail to {} failed!", sendMailRequest.getEmail());
        }
    }
}
