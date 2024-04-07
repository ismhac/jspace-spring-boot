package com.ismhac.jspace.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ismhac.jspace.model.enums.RoleCode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDto {
    int id;
    RoleCode code;
    String name;
    String description;
}

