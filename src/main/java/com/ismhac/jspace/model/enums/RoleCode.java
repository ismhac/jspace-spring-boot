package com.ismhac.jspace.model.enums;

import com.ismhac.jspace.exception.BadRequestException;

import java.util.Objects;

public enum RoleCode {
    SUPER_ADMIN("SU"),
    ADMIN("AD"),
    EMPLOYEE("EM"),
    CANDIDATE("CA");
    private final String code;

    RoleCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final RoleCode[] VALUES;
    static {
        VALUES = values();
    }

    public static RoleCode resolve(String roleTypeCode){
        for(RoleCode roleType : VALUES){
            if(Objects.equals(roleType.code, roleTypeCode)){
                return roleType;
            }
        }
        throw new BadRequestException("No matching role type code constant for [" + roleTypeCode + "]");
    }

    public static RoleCode getValue(String roleTypeKey) {
        for (RoleCode service: VALUES){
            if(Objects.equals(service.toString(), roleTypeKey)){
                return service;
            }
        }
        throw new BadRequestException("No matching role type code constant for [" + roleTypeKey + "]");
    }
}
