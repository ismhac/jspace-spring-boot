package com.ismhac.jspace.event;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SendBillEvent extends ApplicationEvent {
    private SendMailRequest sendMailRequest;
    public SendBillEvent(Object source, SendMailRequest sendMailRequest) {
        super(source);
        this.sendMailRequest = sendMailRequest;
    }
}
