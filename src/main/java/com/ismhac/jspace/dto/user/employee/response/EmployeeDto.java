package com.ismhac.jspace.dto.user.employee.response;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDto {
    UserDto user;

    CompanyDto company;

    Boolean verifiedByCompany;

    Boolean hasFullCredentialInfo;

    Boolean hasCompany;

    Boolean companyEmailVerified;

    Boolean companyVerified;
}
