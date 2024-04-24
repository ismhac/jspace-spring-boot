package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.service.common.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@Configuration
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendMail(SendMailRequest sendMailRequest) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("jspacek20@gmail.com");
            message.setTo(sendMailRequest.getEmail());
            message.setText(sendMailRequest.getBody());
            message.setSubject(sendMailRequest.getSubject());
            javaMailSender.send(message);
            log.info("Send mail to {} successfully!", sendMailRequest.getEmail());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void sendMailWithTemplate(SendMailRequest sendMailRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("body", sendMailRequest.getBody());

            String html = templateEngine.process("AdminVerifyEmail", context);

            helper.setTo(sendMailRequest.getEmail());
            helper.setText(html, true);
            helper.setSubject(sendMailRequest.getSubject());
            helper.setFrom("jspacek20@gmail.com");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
