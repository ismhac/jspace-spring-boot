package com.ismhac.jspace.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String email;

    @JsonIgnore
    private String password;

    private boolean activated;
}
