package com.ismhac.jspace.dto.companyNotification.response;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class CompanyNotificationDto {
    long id;
    int companyId;
    String title;
    String notification;
    boolean read;
    Instant notificationTime;
    Map<String, Object> custom;
}
