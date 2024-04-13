package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.AdminTypeConverter;
import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.primaryKey.AdminId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_admin")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Admin extends BaseEntity {

    @EmbeddedId
    AdminId id;

    @Convert(converter = AdminTypeConverter.class)
    @Column(name = "type", nullable = false)
    AdminType type;

    @Column(name = "email_verified")
    @Builder.Default
    Boolean emailVerified = false;
}
