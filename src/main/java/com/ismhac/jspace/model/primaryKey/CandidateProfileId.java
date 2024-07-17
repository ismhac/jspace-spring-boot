package com.ismhac.jspace.model.primaryKey;

import com.ismhac.jspace.model.Candidate;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class CandidateProfileId implements Serializable {
    @OneToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "user_id")
    Candidate candidate;
}
