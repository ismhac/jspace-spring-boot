package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.AdminTypeConverter;
import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.primaryKey.AdminId;
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

    @EmbeddedId
    private AdminId id;

    @Convert(converter = AdminTypeConverter.class)
    @Column(name = "type", nullable = false)
    private AdminType type;

    @Column(name = "name")
    private String name;

}
