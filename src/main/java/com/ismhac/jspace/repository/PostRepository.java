package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.enums.Location;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("""
            select p as post,(case when exists (
            select 1 from CandidatePostLiked cpl where cpl.id.post.id=p.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) as liked,
            (case when exists (select 1 from CandidatePost cp where cp.id.post.id=p.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) as applied
            from Post p
            """)
    Page<Map<String, Object>> candidateGetPagePost(int candidateId, Pageable pageable);

    @Query("""
            select p as post,(case when exists (
            select 1 from CandidatePostLiked cpl where cpl.id.post.id=p.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) as liked,(case when exists (
            select 1 from CandidatePost cp where cp.id.post.id=p.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) as applied
            from Post p where p.id= :postId
            """)
    Map<String, Object> candidateFindPostById(int candidateId, int postId);

    @Query("""
            select p from Post p where p.company.id = :companyId
            """)
    Page<Post> getPageByCompanyId(int companyId, Pageable pageable);

    @Query("""
            select p as post,
                (case when :candidateId is null then 'guest' else 'candidate' end) as userMode,
                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePostLiked cpl where cpl.id.post.id=p.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) end) as liked,
                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePost cp where cp.id.post.id=p.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) end) as applied
            from Post p
            where p.id = :postId
            """)
    Tuple findPostByIdAndCandidateId(int postId, Integer candidateId);

    @Query("""
            select p as post,
                (case when :candidateId is null then 'guest' else 'candidate' end) as userMode,
                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePostLiked cpl where cpl.id.post.id=p.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) end)as liked,
                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePost cp where cp.id.post.id=p.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) end) as applied
            from Post p
            where (:experience is null or :experience = '' or lower(p.experience) like lower(concat('%', :experience, '%') ) )
                and(:gender is null or :gender = '' or lower(p.gender) like lower(concat('%', :gender, '%') ) )
                and(:jobType is null or :jobType = '' or lower(p.jobType) like lower(concat('%', :jobType, '%')))
                and(:location is null or p.location = :location)
                and(:rank is null or :rank = '' or lower(p.rank) like lower(concat('%', :rank, '%') ) )
                and(:quantity is null or p.quantity = :quantity)
                and(:title is null or :title = '' or lower(p.title) like lower(concat('%', :title, '%') ) )
                and(:companyName is null or :companyName = '' or lower(p.company.name) like lower(concat('%', :companyName, '%') ) )
                and ((:minPay is null and :maxPay is null)
                    or (:minPay is not null and :maxPay is null and p.minPay >= :minPay)
                    or (:minPay is null and :maxPay is not null and p.maxPay <= :maxPay)
                    or (:minPay is not null and :maxPay is not null and p.maxPay between :minPay and :maxPay and p.minPay between :minPay and :maxPay))
            """)
    Page<Map<String, Object>> getPageAndFilter(
            @Param("candidateId") Integer candidateId,
            @Param("experience") String experience,
            @Param("gender") String gender,
            @Param("jobType") String jobType,
            @Param("location") Location location,
            @Param("rank") String rank,
            @Param("quantity") Integer quantity,
            @Param("title") String title,
            @Param("companyName") String companyName,
            @Param("maxPay") Integer maxPay,
            @Param("minPay") Integer minPay,
            Pageable pageable);
}
