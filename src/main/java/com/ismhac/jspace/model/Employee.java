package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.EmployeeId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_employee")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee extends BaseEntity{

    @EmbeddedId
    EmployeeId id;

    String background;

    @Column(name = "background_id")
    String backgroundId;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;

    @Column(name = "verified_by_company")
    @Builder.Default
    Boolean verifiedByCompany = false;
}
