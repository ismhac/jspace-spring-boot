package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.ApplyStatusConverter;
import com.ismhac.jspace.model.enums.ApplyStatus;
import com.ismhac.jspace.model.primaryKey.CandidatePostId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_candidate_post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidatePost extends BaseEntity{

    @EmbeddedId
    CandidatePostId id;

    @Column(name = "resume_id")
    int resumeId;

    @Column(name = "apply_status")
    @Convert(converter = ApplyStatusConverter.class)
    ApplyStatus applyStatus;
}