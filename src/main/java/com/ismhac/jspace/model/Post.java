package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String description;

    @Column(name = "employee_email")
    String employeeEmail;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;
}
