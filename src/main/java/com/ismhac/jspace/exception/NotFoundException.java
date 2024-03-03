package com.ismhac.jspace.exception;

import com.ismhac.jspace.util.response.ApiError;
import com.ismhac.jspace.util.response.Status;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private ApiError apiError;


    public NotFoundException(String errorMessage, Object... args) {
        super(String.format(errorMessage, args));
    }

    public NotFoundException(Status status) {
        apiError = ApiError.builder()
                .errorId(status.getErrorId())
                .errorMessage(status.getErrorMessage())
                .status(status.getStatus())
                .build();
    }
    public NotFoundException(Status status, String errorMessage, Object... args) {
        apiError = ApiError.builder()
                .errorId(status.getErrorId())
                .errorMessage(String.format(errorMessage, args))
                .status(status.getStatus())
                .build();
    }
}
