package com.ismhac.jspace.dto.user.admin.response;

import com.ismhac.jspace.dto.user.response.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminDto {
    UserDto user;
}
