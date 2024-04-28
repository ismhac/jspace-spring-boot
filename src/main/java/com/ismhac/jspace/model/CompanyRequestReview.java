package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.CompanyRequestReviewId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


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

    @Builder.Default
    Boolean reviewed = false;
}
