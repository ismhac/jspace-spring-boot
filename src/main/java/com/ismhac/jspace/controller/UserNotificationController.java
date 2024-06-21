package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.userNotification.response.UserNotificationDto;
import com.ismhac.jspace.service.UserNotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications")
public class UserNotificationController {
    private final UserNotificationService userNotificationService;

    @GetMapping
    public ApiResponse<PageResponse<UserNotificationDto>> getPage(@RequestParam("userId") int userId, Pageable pageable) {
        return ApiResponse.<PageResponse<UserNotificationDto>>builder()
                .result(userNotificationService.getPage(userId, pageable))
                .build();
    }

    @PutMapping("/update/read-status")
    public ApiResponse<UserNotificationDto> updateReadStatus(@RequestParam("userId") int userId, @RequestParam("notificationId") int notificationId, @RequestParam("readStatus") boolean readStatus) {
        return ApiResponse.<UserNotificationDto>builder()
                .result(userNotificationService.updateReadStatus(userId, notificationId, readStatus))
                .build();
    }
}
