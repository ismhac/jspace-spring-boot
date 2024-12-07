package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum NotificationTitle {
    NOTIFICATION_CANDIDATE_UPDATE_APPLY_STATUS("Nhà tuyển dụng cập nhật trạng thái ứng tuyển"),
    NOTIFICATION_ADMIN_NEW_COMPANY("Công ty mới"),
    NOTIFICATION_COMPANY_NEW_CANDIDATE_APPLY("Ứng viên ứng tuyển công việc"),
    NOTIFICATION_COMPANY_NEW_CANDIDATE_FOLLOW("Ứng viên theo dõi công ty"),
    NOTIFICATION_CANDIDATE_COMPANY_HAS_NEW_POST("Công ty mà bạn theo dõi vừa có tin tuyển dụng mới")
    ;
    String title;
}
