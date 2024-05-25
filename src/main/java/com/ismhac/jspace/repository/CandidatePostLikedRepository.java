package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.CandidatePostLiked;
import com.ismhac.jspace.model.primaryKey.CandidatePostLikedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatePostLikedRepository extends JpaRepository<CandidatePostLiked, CandidatePostLikedId> {
}
