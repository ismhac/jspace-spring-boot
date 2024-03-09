package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.EmployeeId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_employee")
public class Employee extends BaseEntity{

    @EmbeddedId
    private EmployeeId id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
}
