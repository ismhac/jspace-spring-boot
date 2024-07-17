package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum NotificationType {
    EMPLOYEE_UPDATE_STATUS_APPLIED(1),
    NEW_COMPANY(2),
    COMPANY_IS_FOLLOWING_HAS_NEW_POST(3)
    ;
    Integer code;
}
