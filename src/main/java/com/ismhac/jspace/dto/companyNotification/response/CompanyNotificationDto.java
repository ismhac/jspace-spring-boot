package com.ismhac.jspace.dto.companyNotification.response;

import lombok.Data;

@Data
public class CompanyNotificationDto {
    long id;
    int companyId;
    String notification;
    boolean read;
}
