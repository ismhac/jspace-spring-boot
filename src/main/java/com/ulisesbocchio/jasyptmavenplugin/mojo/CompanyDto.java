package com.ulisesbocchio.jasyptmavenplugin.mojo;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.ismhac.jspace.model.Company}
 */
@Value
public class CompanyDto implements Serializable {
    Instant createdAt;
    Instant updatedAt;
    Integer createdBy;
    Integer updatedBy;
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