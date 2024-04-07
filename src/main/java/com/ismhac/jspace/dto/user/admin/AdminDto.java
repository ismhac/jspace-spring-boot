package com.ismhac.jspace.dto.user.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ismhac.jspace.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminDto {
    int id;

    UserDto user;
}
