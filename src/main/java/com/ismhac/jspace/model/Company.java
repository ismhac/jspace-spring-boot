package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_company")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name", unique = true)
    String name;

    String background;

    String logo;

    String address;

    String email;

    String phone;

    @Column(columnDefinition = "text")
    String description;

    @Column(name = "company_link")
    String companyLink;

    Boolean verified;
}
