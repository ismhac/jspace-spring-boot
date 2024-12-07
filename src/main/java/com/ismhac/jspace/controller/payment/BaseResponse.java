package com.ismhac.jspace.controller.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class BaseResponse {
    @NonNull
    private Boolean success;

    @NonNull
    private String message;

    public BaseResponse() {
        this.success = true;
        this.message = "";
    }

    public BaseResponse(String message) {
        this.success = true;
        this.message = message;
    }
}
