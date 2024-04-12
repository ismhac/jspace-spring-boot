package com.ismhac.jspace.model.primaryKey;

import com.ismhac.jspace.model.Company;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyRequestReviewId implements Serializable {

    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;
}
