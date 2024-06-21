package com.ismhac.jspace.dto.userNotification.response;

import com.ismhac.jspace.dto.notification.response.NotificationDto;
import lombok.Data;

@Data
public class UserNotificationDto {
    private int userId;
    private NotificationDto notification;
    private boolean read;
}
