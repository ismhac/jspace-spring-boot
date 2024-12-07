package com.ismhac.jspace.dto.company.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class CompanyCreateRequest {
    String name;
    String address;
    @Email
    String email;
    String phone;
    String companySize;
    String description;
    String companyLink;
    String background;
    String backgroundId;
    String logo;
    String logoId;
}
