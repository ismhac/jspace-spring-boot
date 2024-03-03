package com.ismhac.jspace.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegisterRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
