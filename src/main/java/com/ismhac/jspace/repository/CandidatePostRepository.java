package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.CandidatePost;
import com.ismhac.jspace.model.primaryKey.CandidatePostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CandidatePostRepository extends JpaRepository<CandidatePost, CandidatePostId> {

    @Query("""
            select cp
            from CandidatePost cp
            where cp.id.candidate.id.user.id = ?1 and cp.id.post.id = ?2
            """)
    Optional<CandidatePost> findByCandidateIdAndPostId(int candidateId, int PostId);
}
