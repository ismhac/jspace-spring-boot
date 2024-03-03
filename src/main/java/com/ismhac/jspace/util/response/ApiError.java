package com.ismhac.jspace.util.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiError {
    private String errorId;
    private String errorMessage;
    private HttpStatus status;
    private Object data;
}
