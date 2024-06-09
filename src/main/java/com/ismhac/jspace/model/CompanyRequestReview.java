package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.CompanyRequestReviewId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_company_request_review")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyRequestReview extends BaseEntity{
    @EmbeddedId
    CompanyRequestReviewId id;
    @Column(name = "request_date")
    LocalDate requestDate;
    @Builder.Default
    Boolean reviewed = false;
}
