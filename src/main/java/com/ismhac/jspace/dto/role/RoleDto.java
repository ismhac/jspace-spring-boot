package com.ismhac.jspace.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ismhac.jspace.model.enums.RoleCode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDto {

    @JsonProperty("id")
    int id;

    @JsonProperty("code")
    RoleCode code;

    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;
}

