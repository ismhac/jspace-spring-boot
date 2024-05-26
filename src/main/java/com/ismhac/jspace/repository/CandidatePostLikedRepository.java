package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.CandidatePostLiked;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.primaryKey.CandidatePostLikedId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CandidatePostLikedRepository extends JpaRepository<CandidatePostLiked, CandidatePostLikedId> {

    @Query("""
            select cpl
            from CandidatePostLiked cpl
            where cpl.id.candidate.id.user.id = ?1
                and cpl.id.post.id = ?2
            """)
    Optional<CandidatePostLiked> findByCandiDateIdAndPostId(int candidateId, int postId);

    @Modifying
    @Query("""
            delete
            from CandidatePostLiked cpl
            where cpl.id.candidate.id.user.id = ?1
                and cpl.id.post.id = ?2
            """)
    int deleteByCandidateIdAndPostId(int candidateId, int postId);

    @Query("""
            select cpl.id.post
            from CandidatePostLiked cpl
            where cpl.id.candidate.id.user.id = ?1
            """)
    Page<Post> getPagePostByCandidateId(int candidateId, Pageable pageable);
}
