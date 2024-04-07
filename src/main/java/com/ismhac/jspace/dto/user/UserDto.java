package com.ismhac.jspace.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ismhac.jspace.model.enums.RoleCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private int id;
    private String username;
    private String email;
    private boolean activated;
    private RoleCode role;
}
