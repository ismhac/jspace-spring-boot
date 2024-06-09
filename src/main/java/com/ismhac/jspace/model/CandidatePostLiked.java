package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.CandidatePostLikedId;
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
@Table(name = "tbl_candidate_post_liked")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidatePostLiked {
    @EmbeddedId
    CandidatePostLikedId id;
}
