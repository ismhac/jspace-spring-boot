package com.ismhac.jspace.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum AdminType {
    ROOT("RO"),
    BASIC("BA")
    ;
    private final String code;

    AdminType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
