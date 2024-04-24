package com.ismhac.jspace.dto.company.response;

import com.ismhac.jspace.dto.common.response.BaseEntityDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDto extends BaseEntityDto {
    int id;
    String name;
    String background;
    String logo;
    String address;
    String email;
    String phone;
    String description;
    String companyLink;
    Boolean emailVerified;
    Boolean verifiedByAdmin;
}
