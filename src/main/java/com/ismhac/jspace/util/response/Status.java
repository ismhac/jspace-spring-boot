package com.ismhac.jspace.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Status {

    NOT_FOUND("404", "NOT_FOUND", HttpStatus.NOT_FOUND),

    /* COMPANY */
    COMPANY_EXIST_NAME("400", "COMPANY_NAME_EXITS", HttpStatus.BAD_REQUEST);

    private final String errorId;
    private final String errorMessage;
    private final HttpStatus status;
}
