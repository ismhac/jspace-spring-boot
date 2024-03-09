package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.RoleCodeConverter;
import com.ismhac.jspace.model.enums.RoleCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_role")
public class Role extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", unique = true, nullable = false)
    @Convert(converter = RoleCodeConverter.class)
    private RoleCode code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
