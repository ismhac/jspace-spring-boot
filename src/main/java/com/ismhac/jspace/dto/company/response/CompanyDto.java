package com.ismhac.jspace.dto.company.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDto {

    int id;

    String name;

    String background;

    String logo;

    String address;

    String email;

    String phone;

    String description;

    String companyLink;

    Boolean verified;
}
