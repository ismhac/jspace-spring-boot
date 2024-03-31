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

    @Column(name = "background")
    String background;

    @Column(name = "logo")
    String logo;

    @Column(name = "address")
    String address;

//    @OneToMany
//    Set<Employee> employees;
//
//    @OneToMany
//    Set<Post> posts;
}
