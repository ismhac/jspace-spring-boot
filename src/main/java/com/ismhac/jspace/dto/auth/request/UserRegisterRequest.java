package com.ismhac.jspace.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

    private String username;

    @NotBlank(message = "PASSWORD_IS_BLANK")
    @Size(min = 8, max = 20, message = "INVALID_PASSWORD_LENGTH")
    private String password;
}
