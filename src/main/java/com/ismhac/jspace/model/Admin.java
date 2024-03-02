package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.AdminID;
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
    private AdminID adminID;

    @Column(name = "name")
    private String name;
}
