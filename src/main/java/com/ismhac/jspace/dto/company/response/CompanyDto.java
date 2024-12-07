package com.ismhac.jspace.dto.company.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDto {
    int id;
    String name;
    String background;
    String backgroundId;
    String logo;
    String logoId;
    String address;
    String email;
    String phone;
    String companySize;
    String description;
    String companyLink;
    Integer trialPost;
    Boolean emailVerified;
    Boolean verifiedByAdmin;
    Boolean activateStatus;
    int followerNumber;
}
