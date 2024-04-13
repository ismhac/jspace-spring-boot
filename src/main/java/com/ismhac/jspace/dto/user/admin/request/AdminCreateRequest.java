package com.ismhac.jspace.dto.user.admin.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AdminCreateRequest {

    @NotBlank
    String username;

    @NotBlank
    String password;

    @NotBlank
    @Email
    String email;

    @JsonIgnore
    String subject;

    @JsonIgnore
    String body;

    @NotBlank
    String returnUrl;
}
