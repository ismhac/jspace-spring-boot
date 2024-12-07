package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RoleCode {
    SUPER_ADMIN("SU", "super admin"),
    ADMIN("AD", "admin"),
    EMPLOYEE("EM", "employee"),
    CANDIDATE("CA", "candidate");
    String code;
    String name;
}
