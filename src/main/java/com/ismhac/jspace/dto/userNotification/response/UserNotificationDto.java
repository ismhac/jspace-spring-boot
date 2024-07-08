package com.ismhac.jspace.dto.userNotification.response;

import com.ismhac.jspace.dto.notification.response.NotificationDto;
import lombok.Data;

import java.time.Instant;

@Data
public class UserNotificationDto {
    private int userId;
    private NotificationDto notification;
    private boolean read;
    private Instant notificationTime;
}
