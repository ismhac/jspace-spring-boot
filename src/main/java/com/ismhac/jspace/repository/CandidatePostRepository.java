package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.CandidatePost;
import com.ismhac.jspace.model.enums.ApplyStatus;
import com.ismhac.jspace.model.enums.PostStatus;
import com.ismhac.jspace.model.primaryKey.CandidatePostId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CandidatePostRepository extends JpaRepository<CandidatePost, CandidatePostId> {

    @Query("""
            select cp from CandidatePost cp where cp.id.candidate.id.user.id = ?1 and cp.id.post.id = ?2
            """)
    Optional<CandidatePost> findByCandidateIdAndPostId(int candidateId, int PostId);

    @Query("""
            select cp.id.post as post,cp.applyStatus as applyStatus from CandidatePost cp where cp.id.candidate.id.user.id = ?1
            """)
    Page<Map<String, Object>> candidateGetPageAppliedPost(int candidateId, Pageable pageable);

    @Query("""
            select cp from CandidatePost cp where cp.id.post.company.id = ?1
            """)
    Page<CandidatePost> getPageCandidateAppliedPost(int companyId, Pageable pageable);

    @Query("""
            select cp
            from CandidatePost cp
            where cp.id.post.id = :postId
                and (:candidateName is null or :candidateName = '' or lower(cp.id.candidate.id.user.name) like lower(concat('%',:candidateName, '%')) )
                and (:candidateEmail is null or :candidateEmail = '' or lower(cp.id.candidate.id.user.email) like lower(concat('%',:candidateEmail, '%')))
                and (:candidatePhoneNumber is null or :candidatePhoneNumber = '' or lower(cp.id.candidate.id.user.phone) like lower(concat('%',:candidatePhoneNumber, '%')))
                and (:postStatus is null or (:postStatus is not null and cp.id.post.postStatus = :postStatus))
                and (:applyStatus is null or (:applyStatus is not null and cp.applyStatus = :applyStatus))
            """)
    Page<CandidatePost> getPageCandidateAppliedByPostId(int postId, String candidateName, String candidateEmail, String candidatePhoneNumber, PostStatus postStatus, ApplyStatus applyStatus, Pageable pageable);

    @Query("""
            select cp.id.candidate from CandidatePost cp where cp.id.post.id = ?1
            """)
    List<Candidate> getListCandidateByPostId(int postId);
}
