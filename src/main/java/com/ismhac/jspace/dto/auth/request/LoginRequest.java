package com.ismhac.jspace.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    String username;

    @NotBlank(message = "PASSWORD_IS_BLANK")
    @Size(min = 8, max = 20, message = "INVALID_PASSWORD_LENGTH")
    String password;
}
