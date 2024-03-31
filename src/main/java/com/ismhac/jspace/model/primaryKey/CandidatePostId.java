package com.ismhac.jspace.model.primaryKey;

import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.Post;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CandidatePostId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "user_id")
    Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    Post post;
}
