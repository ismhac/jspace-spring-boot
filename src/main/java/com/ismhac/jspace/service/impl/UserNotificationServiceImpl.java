package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.userNotification.response.UserNotificationDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.UserNotificationMapper;
import com.ismhac.jspace.model.UserNotification;
import com.ismhac.jspace.repository.UserNotificationRepository;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.UserNotificationService;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationServiceImpl implements UserNotificationService {
    private final UserNotificationRepository userNotificationRepository;
    private final PageUtils pageUtils;

    @Override
    public PageResponse<UserNotificationDto> getPage(int userId, Pageable pageable) {
        return pageUtils.toPageResponse(UserNotificationMapper.instance.ePageToDtoPage(userNotificationRepository.findAllById_UserId(userId, pageUtils.adjustPageable(pageable))));
    }

    @Override
    public UserNotificationDto updateReadStatus(int userId, int notificationId, boolean readStatus) {
        UserNotification userNotification = userNotificationRepository.findById_UserIdAndId_NotificationId(userId, notificationId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER_NOTIFICATION));
        userNotification.setRead(readStatus);
        return UserNotificationMapper.instance.eToDto(userNotificationRepository.save(userNotification));
    }
}

