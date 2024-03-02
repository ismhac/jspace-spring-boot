package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_admin")
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne
    @JoinColumn(name = "base_user_id", referencedColumnName = "id", nullable = false)
    private BaseUser baseUser;

    private String name;
}
