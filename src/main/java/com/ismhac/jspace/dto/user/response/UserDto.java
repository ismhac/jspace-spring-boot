package com.ismhac.jspace.dto.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ismhac.jspace.dto.common.response.BaseEntityDto;
import com.ismhac.jspace.model.enums.RoleCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto extends BaseEntityDto {
    int id;

    String username;

    String email;

    String name;

    String picture;

    String phone;

    boolean activated;

    RoleCode role;
}
