package com.ismhac.jspace.model.enums;

import com.ismhac.jspace.exception.BadRequestException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum AdminType {
    super_admin("su"),
    admin("ad"),
    employee("em"),
    candidate("ca");


    private static final Map<String, AdminType> CODE_TO_ENUM = new HashMap<>();
    private static final Map<String, AdminType> NAME_TO_ENUM = new HashMap<>();

    static {
        for (AdminType adminType : values()) {
            CODE_TO_ENUM.put(adminType.code, adminType);
            NAME_TO_ENUM.put(adminType.name(), adminType);
        }
    }

    private final String code;

    AdminType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static AdminType resolve(String adminTypeCode) throws BadRequestException {
        return resolveEnum(adminTypeCode, CODE_TO_ENUM);
    }

    public static AdminType getValue(String roleTypeKey) throws BadRequestException {
        return resolveEnum(roleTypeKey, NAME_TO_ENUM);
    }

    private static AdminType resolveEnum(String key, Map<String, AdminType> map) throws BadRequestException {
        AdminType result = map.get(key);
        if (result == null) {
            throw new BadRequestException("No matching admin type code constant for [" + key + "]");
        }
        return result;
    }
}
