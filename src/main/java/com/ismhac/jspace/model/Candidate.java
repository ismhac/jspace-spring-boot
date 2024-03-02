package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_candidate")
public class Candidate extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne
    @JoinColumn(name = "base_user_id", referencedColumnName = "id", nullable = false)
    private BaseUser baseUser;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "phone_number")
    private String phoneNumber;
}
