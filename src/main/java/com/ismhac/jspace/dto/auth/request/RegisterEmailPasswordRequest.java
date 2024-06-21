package com.ismhac.jspace.dto.auth.request;

import com.ismhac.jspace.model.enums.RoleCode;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterEmailPasswordRequest {
    @NotBlank
    @Email
    String email;
    @NotBlank
    @Size(min = 8, max = 20)
    String password;
    RoleCode roleCode;
}
