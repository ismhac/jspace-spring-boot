package com.ismhac.jspace.event;

import com.ismhac.jspace.dto.user.admin.request.AdminCreateRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SendMailCreateAdminEvent extends ApplicationEvent {

    private AdminCreateRequest adminCreateRequest;
    public SendMailCreateAdminEvent(Object source, AdminCreateRequest adminCreateRequest) {
        super(source);
        this.adminCreateRequest = adminCreateRequest;
    }
}
