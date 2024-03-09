package com.ismhac.jspace.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("is_activated")
    private boolean activated;

    @JsonProperty("role_code")
    private String roleCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatar")
    private String avatar;
}
