package com.ismhac.jspace.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    INVALID_KEY(1001,"Invalid message key"),
    NOT_FOUND_SUPPER_ADMIN(1002, "Not found super admin"),
    USER_EXISTED(1003, "User is existed"),

    NOT_FOUND_ROLE(1004, "Not found role"),

    NOT_EMAIL_FORMAT(1005, "Not email format"),

    PASSWORD_IS_BLANK(1006, "Password is blank"),

    INVALID_PASSWORD_LENGTH(1007, "Password length must be from 8 to 20 characters"),

    INVALID_ROLE_REGISTER(1008, "Can only register with employee and candidate role"),

    NOT_FOUND_REFRESH_TOKEN(1009, "Not found refresh token"),

    REFRESH_TOKEN_EXPIRE(1010, "Token Expire"),

    MISSING_HEADER_VALUE(1011, "Missing header value"),

    WRONG_USERNAME_OR_PASSWORD(1012, "wrong username or password")
    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
