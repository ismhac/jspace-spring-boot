package com.ismhac.jspace.exception;

import com.ismhac.jspace.util.response.ApiError;
import com.ismhac.jspace.util.response.Status;
import lombok.Getter;

@Getter
public class BadRequestException extends Exception{
    public BadRequestException(String message){
        super(message);
    }
}

