package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.common.request.SendMailRequest;

public interface MailService {
    void sendMail(SendMailRequest sendMailRequest);
}
