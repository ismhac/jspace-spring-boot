package com.ismhac.jspace.model.primaryKey;

import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.Post;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class CandidatePostLikedId {
    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "user_id", nullable = false)
    Candidate candidate;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    Post post;
}

