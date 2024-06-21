package com.ismhac.jspace.dto.auth.request;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginEmailPasswordRequest {
    @NotBlank
    @Email
    String email;
    @NotBlank
    @Size(min = 8, max = 20)
    String password;
}
