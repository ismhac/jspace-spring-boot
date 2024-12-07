package com.ismhac.jspace.event;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RequestCompanyVerifyEmailEvent extends ApplicationEvent {
    private SendMailRequest sendMailRequest;

    public RequestCompanyVerifyEmailEvent(Object source, SendMailRequest sendMailRequest) {
        super(source);
        this.sendMailRequest = sendMailRequest;
    }
}
