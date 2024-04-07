package com.ismhac.jspace.dto.user.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ismhac.jspace.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDto {
    int id;
    UserDto user;
}
