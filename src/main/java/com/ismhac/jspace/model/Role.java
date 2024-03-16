package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.RoleCodeConverter;
import com.ismhac.jspace.model.enums.RoleCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "code", unique = true, nullable = false)
    @Convert(converter = RoleCodeConverter.class)
    RoleCode code;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;


}
