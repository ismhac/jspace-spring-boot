package com.ismhac.jspace.dto.notification.response;

import com.ismhac.jspace.model.enums.NotificationType;
import lombok.Data;

import java.util.Map;

@Data
public class NotificationDto {
    long id;
    String content;
    NotificationType type;
    String title;
    Map<String, Object> custom;
}
