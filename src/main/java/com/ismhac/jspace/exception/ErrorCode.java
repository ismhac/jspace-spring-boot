package com.ismhac.jspace.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001,"Invalid message key", HttpStatus.BAD_REQUEST),
    NOT_FOUND_SUPPER_ADMIN(1002, "Not found super admin", HttpStatus.NOT_FOUND),
    USER_EXISTED(1003, "User is existed",  HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(1004, "user not existed", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(1004, "unauthenticated", HttpStatus.UNAUTHORIZED),

    INVALID_TOKEN(1004, "Invalid token", HttpStatus.BAD_REQUEST),

    NOT_FOUND_ROLE(1004, "Not found role", HttpStatus.NOT_FOUND),

    NOT_EMAIL_FORMAT(1005, "Not email format", HttpStatus.BAD_REQUEST),

    PASSWORD_IS_BLANK(1006, "Password is blank", HttpStatus.BAD_REQUEST),

    INVALID_PASSWORD_LENGTH(1007, "Password length must be from 8 to 20 characters", HttpStatus.BAD_REQUEST),

    INVALID_ROLE_REGISTER(1008, "Can only register with employee or candidate role", HttpStatus.BAD_REQUEST),

    NOT_FOUND_REFRESH_TOKEN(1009, "Not found refresh token", HttpStatus.NOT_FOUND),

    REFRESH_TOKEN_EXPIRE(1010, "Token Expire", HttpStatus.BAD_REQUEST),

    MISSING_HEADER_VALUE(1011, "Missing header value", HttpStatus.BAD_REQUEST),

    WRONG_USERNAME_OR_PASSWORD(1012, "wrong username or password", HttpStatus.UNAUTHORIZED ),

    UNAUTHORIZED(1013, "Do not permission", HttpStatus.UNAUTHORIZED),

    NOT_FOUND_USER(1014, "Not found user", HttpStatus.NOT_FOUND),

    NOT_FOUND_EMPLOYEE(1015, "Not found employee", HttpStatus.NOT_FOUND),

    NOT_FOUND_COMPANY(1016, "Not found company", HttpStatus.NOT_FOUND),

    SEND_MAIL_FAIL(1017, "Send mail fail!", HttpStatus.BAD_REQUEST),

    UPLOAD_FILE_FAIL(1018, "Upload file fail", HttpStatus.BAD_REQUEST)
    ;
    private final int code;
    private final String message;

    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode){
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
