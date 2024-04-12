package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.CandidateFollowCompanyId;
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
@Table(name = "tbl_candidate_follow_company")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateFollowCompany extends BaseEntity{

    @EmbeddedId
    CandidateFollowCompanyId id;
}
