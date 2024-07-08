package com.ismhac.jspace.dto.companyNotification.response;

import lombok.Data;

import java.time.Instant;

@Data
public class CompanyNotificationDto {
    long id;
    int companyId;
    String title;
    String notification;
    boolean read;
    Instant notificationTime;
}
