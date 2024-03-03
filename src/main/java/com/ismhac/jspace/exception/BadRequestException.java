package com.ismhac.jspace.exception;

import com.ismhac.jspace.util.response.ApiError;
import com.ismhac.jspace.util.response.Status;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{

    private ApiError apiError;

    public BadRequestException(String errorMessage, Object... args) {
        super(String.format(errorMessage, args));
    }

    public BadRequestException(Status status) {
        apiError = ApiError.builder()
                .errorId(status.getErrorId())
                .errorMessage(status.getErrorMessage())
                .status(status.getStatus())
                .build();
    }

    public BadRequestException(Status status, Object data) {
        super(status.getErrorId().concat(" - ").concat(status.getErrorMessage()));
        apiError = ApiError.builder()
                .errorId(status.getErrorId())
                .errorMessage(status.getErrorMessage())
                .status(status.getStatus())
                .data(data)
                .build();
    }

    public BadRequestException(Status status, String errorMessage, Object... args) {
        super(status.getErrorId().concat(" - ").concat(status.getErrorMessage()));
        apiError = ApiError.builder()
                .errorId(status.getErrorId())
                .errorMessage(String.format(errorMessage, args))
                .status(status.getStatus())
                .build();
    }
}

