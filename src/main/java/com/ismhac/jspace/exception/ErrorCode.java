package com.ismhac.jspace.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(HttpStatus.BAD_REQUEST.value(), "Invalid message key", HttpStatus.BAD_REQUEST),
    NOT_FOUND_SUPPER_ADMIN(1002, "Not found super admin", HttpStatus.NOT_FOUND),
    USER_EXISTED(HttpStatus.BAD_REQUEST.value(), "User is existed",  HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "user not existed", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "unauthenticated", HttpStatus.UNAUTHORIZED),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "Invalid token", HttpStatus.BAD_REQUEST),

    NOT_FOUND_ROLE(HttpStatus.NOT_FOUND.value(), "Not found role", HttpStatus.NOT_FOUND),

    NOT_EMAIL_FORMAT(1005, "Not email format", HttpStatus.BAD_REQUEST),

    PASSWORD_IS_BLANK(1006, "Password is blank", HttpStatus.BAD_REQUEST),

    INVALID_PASSWORD_LENGTH(1007, "Password length must be from 8 to 20 characters", HttpStatus.BAD_REQUEST),

    INVALID_ROLE_REGISTER(1008, "Can only register with employee or candidate role", HttpStatus.BAD_REQUEST),

    NOT_FOUND_REFRESH_TOKEN(1009, "Not found refresh token", HttpStatus.NOT_FOUND),

    REFRESH_TOKEN_EXPIRE(1010, "Token Expire", HttpStatus.BAD_REQUEST),

    MISSING_HEADER_VALUE(1011, "Missing header value", HttpStatus.BAD_REQUEST),

    WRONG_USERNAME_OR_PASSWORD(1012, "wrong username or password", HttpStatus.UNAUTHORIZED ),

    UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "Do not permission",HttpStatus.FORBIDDEN),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "Not found user", HttpStatus.NOT_FOUND),

    NOT_FOUND_EMPLOYEE(1015, "Not found employee", HttpStatus.NOT_FOUND),

    NOT_FOUND_COMPANY(1016, "Not found company", HttpStatus.NOT_FOUND),

    SEND_MAIL_FAIL(1017, "Send mail fail!", HttpStatus.BAD_REQUEST),

    UPLOAD_FILE_FAIL(1018, "Upload file fail", HttpStatus.BAD_REQUEST),

    USER_HAS_BEEN_BLOCKED(HttpStatus.BAD_REQUEST.value(), "User has been blocked", HttpStatus.BAD_REQUEST),

    EMAIL_HAS_BEEN_USED(HttpStatus.BAD_REQUEST.value(), "Email has been used", HttpStatus.BAD_REQUEST),

    TOKEN_EXPIRE(HttpStatus.BAD_REQUEST.value(), "Token expire", HttpStatus.BAD_REQUEST),

    EMAIL_HAS_BEEN_VERIFIED(HttpStatus.OK.value(), "Email has been verified", HttpStatus.OK),

    COMPANY_EXISTED(HttpStatus.BAD_REQUEST.value(), "Company is existed", HttpStatus.BAD_REQUEST),

    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND.value(), "Not found product", HttpStatus.NOT_FOUND),

    DELETE_FILE_FAIL(HttpStatus.BAD_REQUEST.value(), "Delete file fail", HttpStatus.BAD_REQUEST),

    INVALID_FILE_ID(HttpStatus.BAD_REQUEST.value(), "Invalid file id", HttpStatus.BAD_REQUEST),

    NOT_FOUND_SKILL(HttpStatus.NOT_FOUND.value(), "Not found skill", HttpStatus.NOT_FOUND),

    TRIAL_POST_EXPIRED(HttpStatus.BAD_REQUEST.value(), "The number of trial recruitment posts has expired", HttpStatus.BAD_REQUEST),

    NOT_FOUND_PURCHASED_PRODUCT(HttpStatus.NOT_FOUND.value(), "Not found purchased product", HttpStatus.NOT_FOUND),

    INVALID_PURCHASED_PRODUCT(HttpStatus.BAD_REQUEST.value(), "Invalid purchased product", HttpStatus.BAD_REQUEST),

    PURCHASED_PRODUCT_EXPIRE(HttpStatus.BAD_REQUEST.value(), "Purchased product expire", HttpStatus.BAD_REQUEST),

    NOT_FOUND_RESUME(HttpStatus.NOT_FOUND.value(), "Not found resume", HttpStatus.NOT_FOUND),

    NOT_FOUND_POST(HttpStatus.NOT_FOUND.value(), "Not found post", HttpStatus.NOT_FOUND),

    NOT_FOUND_POST_LIKED(HttpStatus.NOT_FOUND.value(), "Not found post liked", HttpStatus.NOT_FOUND)
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
