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
    NOTIFICATION_ADMIN_NEW_COMPANY("Công ty mới")
    ;
    String title;
}
