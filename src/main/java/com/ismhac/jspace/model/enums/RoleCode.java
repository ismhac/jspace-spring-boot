package com.ismhac.jspace.model.enums;

public enum RoleCode {
    SUPER_ADMIN("SU", "super admin"),
    ADMIN("AD", "admin"),
    EMPLOYEE("EM", "employee"),
    CANDIDATE("CA", "candidate")
    ;

    private final String code;
    private final String name;

    RoleCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
