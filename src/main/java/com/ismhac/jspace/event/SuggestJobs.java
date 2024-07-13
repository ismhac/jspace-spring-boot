package com.ismhac.jspace.event;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SuggestJobs extends ApplicationEvent {
    private SendMailRequest sendMailRequest;
    public SuggestJobs(Object source, SendMailRequest sendMailRequest) {
        super(source);
        this.sendMailRequest = sendMailRequest;
    }
}