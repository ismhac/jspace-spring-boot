package com.ismhac.jspace.model.enums;

import com.ismhac.jspace.exception.BadRequestException;

import java.util.HashMap;
import java.util.Map;


public enum RoleCode {
    super_admin("su"),
    admin("ad"),
    employee("em"),
    candidate("ca");


    private static final Map<String, RoleCode> CODE_TO_ENUM = new HashMap<>();
    private static final Map<String, RoleCode> NAME_TO_ENUM = new HashMap<>();

    static {
        for (RoleCode roleType : values()) {
            CODE_TO_ENUM.put(roleType.code, roleType);
            NAME_TO_ENUM.put(roleType.name(), roleType);
        }
    }

    private final String code;

    RoleCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static RoleCode resolve(String roleTypeCode) throws BadRequestException {
        return resolveEnum(roleTypeCode, CODE_TO_ENUM);
    }

    public static RoleCode getValue(String roleTypeKey) throws BadRequestException {
        return resolveEnum(roleTypeKey, NAME_TO_ENUM);
    }

    private static RoleCode resolveEnum(String key, Map<String, RoleCode> map) throws BadRequestException {
        RoleCode result = map.get(key);
        if (result == null) {
            throw new BadRequestException("No matching role type code constant for [" + key + "]");
        }
        return result;
    }
}
