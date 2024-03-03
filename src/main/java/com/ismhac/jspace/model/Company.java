package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_company")
public class Company extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int  id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "background")
    private String background;

    @Column(name = "logo")
    private String logo;

    @Column(name = "address")
    private String address;
}
