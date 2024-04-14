package com.ismhac.jspace.dto.user.employee.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateRequest {
    private String email;

    private String name;

    private String phone;
}
