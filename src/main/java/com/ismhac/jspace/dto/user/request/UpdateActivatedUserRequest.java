package com.ismhac.jspace.dto.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateActivatedUserRequest {

    @NotNull
    int userId;

    @NotNull
    Boolean activated;
}
