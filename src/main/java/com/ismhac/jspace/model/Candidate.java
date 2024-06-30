package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.CandidateId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_candidate")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Candidate extends BaseEntity {
    @EmbeddedId
    CandidateId id;
    @OneToOne
    @JoinColumn(name = "default_resume_id", referencedColumnName = "id")
    Resume defaultResume;
    @Column(name = "public_profile")
    @Builder.Default
    Boolean publicProfile = false;
}
