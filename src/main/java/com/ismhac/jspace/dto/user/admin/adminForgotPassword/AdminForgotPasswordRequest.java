package com.ismhac.jspace.dto.user.admin.adminForgotPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminForgotPasswordRequest {

    @NotBlank
    @Email
    String email;

    String subject;

    String body;

    @NotBlank
    String returnUrl;
}
