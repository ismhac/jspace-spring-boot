package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.userNotification.response.UserNotificationDto;
import org.springframework.data.domain.Pageable;

public interface UserNotificationService {
    PageResponse<UserNotificationDto> getPage(int userId, Pageable pageable);

    UserNotificationDto updateReadStatus(int userId, int notificationId,boolean readStatus);
}
