package com.ismhac.jspace.dto.employee;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSignInRequest {
    private String email;
    private String password;
}
