package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.PostStatus;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("""
            select p as post,(case when exists (
            select 1 from CandidatePostLiked cpl where cpl.id.post.id=p.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) as liked,
            (case when exists (select 1 from CandidatePost cp where cp.id.post.id=p.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) as applied
            from Post p
            where p.closeDate >= :now
                and p.postStatus = :postStatus
                and p.deleted = false
            """)
    Page<Map<String, Object>> candidateGetPagePost(@Param("candidateId") int candidateId, @Param("now") LocalDate now, PostStatus postStatus,Pageable pageable);

    @Query("""
            select p as post,(case when exists (
            select 1 from CandidatePostLiked cpl where cpl.id.post.id=p.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) as liked,(case when exists (
            select 1 from CandidatePost cp where cp.id.post.id=p.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) as applied
            from Post p where p.id= :postId
            and p.deleted = false
            """)
    Map<String, Object> candidateFindPostById(@Param("candidateId") int candidateId, @Param("postId") int postId);

    @Query(value = """
            select p.*
            from tbl_post p
            where p.company_id = :companyId
                and (:title is null or :title = '' or lower(p.title) like lower(concat('%', :title,  '%') ) )
                and (:postStatus is null or p.post_status = :postStatus)
                and (
                        (:duration is null)
                        or (:duration = 'expired' and (p.close_date < :now))
                        or (:duration = 'unexpired' and (p.close_date >= :now))
                    )
                and p.deleted = false
            """, nativeQuery = true, countQuery = """
            select p.*
            from tbl_post p
            where p.company_id = :companyId
                and (:title is null or :title = '' or lower(p.title) like lower(concat('%', :title,  '%') ) )
                and (:postStatus is null or :postStatus = '' or p.post_status = :postStatus)
                and (
                        (:duration is null)
                        or (:duration = 'expired' and (p.close_date < :now))
                        or (:duration = 'unexpired' and (p.close_date >= :now))
                    )
                and p.deleted = false
            """)
    Page<Post> getPageByCompanyId(@Param("companyId") int companyId, String title, String postStatus, String duration, LocalDate now, Pageable pageable);

    @Query("""
            select p as post,
                (case when :candidateId is null then 'guest' else 'candidate' end) as userMode,
                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePostLiked cpl where cpl.id.post.id=p.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) end) as liked,
                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePost cp where cp.id.post.id=p.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) end) as applied
            from Post p
            where p.id = :postId
            and p.deleted = false
            """)
    Tuple findPostByIdAndCandidateId(@Param("postId") int postId, @Param("candidateId") Integer candidateId);


//    @Query(value = """
//            select p as post,
//                (case when :candidateId is null then 'guest' else 'candidate' end) as userMode,
//                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePostLiked cpl where cpl.id.post.id=p.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) end)as liked,
//                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePost cp where cp.id.post.id=p.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) end) as applied
//            from Post p
//            join PostSkill ps on p.id = ps.id.post.id
//            join Skill s on s.id = ps.id.skill.id
//            where p.deleted = false
//                and (:experience is null or :experience = '' or lower(p.experience) like lower(concat('%', :experience, '%') ) )
//                and(:gender is null or :gender = '' or lower(p.gender) like lower(concat('%', :gender, '%') ) )
//                and(:jobType is null or :jobType = '' or lower(p.jobType) like lower(concat('%', :jobType, '%')))
//                and(:location is null or p.location = :location)
//                and(:rank is null or :rank = '' or lower(p.rank) like lower(concat('%', :rank, '%') ) )
//                and(:quantity is null or p.quantity = :quantity)
//                and(:title is null or :title = '' or lower(p.title) like lower(concat('%', :title, '%') ) )
//                and(:companyName is null or :companyName = '' or lower(p.company.name) like lower(concat('%', :companyName, '%') ) )
//                and (
//                        (:minPay is null and :maxPay is null)
//                        or
//                        (
//                            (:minPay is not null and :maxPay is not null and  (p.minPay between :minPay and :maxPay or p.maxPay between :minPay and :maxPay))
//                        )
//                        or
//                        (
//                            (:maxPay is null and :minPay is not null and p.minPay >= :minPay)
//                        )
//                        or
//                        (
//                            (:minPay is null and :maxPay is not null and p.maxPay <= :maxPay)
//                        )
//                    )
//                and (:skills is null or (:skills is not null and s.id in :skills))
//                and p.closeDate >= :now
//                and p.postStatus = :postStatus
//            group by p.id, p.createdAt, p.createdBy, p.updatedAt, p.updatedBy, p.closeDate, p.description, p.experience, p.gender, p.jobType, p.location, p.maxPay, p.maxPay, p.openDate, p.postStatus, p.quantity, p.rank, p.title, p.company, p.deleted
//""")
//    Page<Map<String, Object>> getPageAndFilter(
//            @Param("candidateId") Integer candidateId,
//            @Param("experience") String experience,
//            @Param("gender") String gender,
//            @Param("jobType") String jobType,
//            @Param("location") Location location,
//            @Param("rank") String rank,
//            @Param("quantity") Integer quantity,
//            @Param("title") String title,
//            @Param("companyName") String companyName,
//            @Param("maxPay") Integer maxPay,
//            @Param("minPay") Integer minPay,
//            @Param("now") LocalDate now,
//            @Param("postStatus") PostStatus postStatus,
//            @Param("skills") List<Integer> skills_id,
//            Pageable pageable);

    @Query(value = """
            select p as post,
                (case when :candidateId is null then 'guest' else 'candidate' end) as userMode,
                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePostLiked cpl where cpl.id.post.id=ps.id.post.id and cpl.id.candidate.id.user.id= :candidateId) then true else false end) end)as liked,
                (case when :candidateId is null then null else (case when exists (select 1 from CandidatePost cp where cp.id.post.id=ps.id.post.id and cp.id.candidate.id.user.id= :candidateId) then true else false end) end) as applied
            from PostSkill ps
            join Post p on ps.id.post.id = p.id
            join Skill s on s.id = ps.id.skill.id
            where ps.id.post.deleted = false
                and (:experience is null or :experience = '' or lower(ps.id.post.experience) like lower(concat('%', :experience, '%') ) )
                and(:gender is null or :gender = '' or lower(ps.id.post.gender) like lower(concat('%', :gender, '%') ) )
                and(:jobType is null or :jobType = '' or lower(ps.id.post.jobType) like lower(concat('%', :jobType, '%')))
                and(:location is null or ps.id.post.location = :location)
                and(:rank is null or :rank = '' or lower(ps.id.post.rank) like lower(concat('%', :rank, '%') ) )
                and(:quantity is null or ps.id.post.quantity = :quantity)
                and(:title is null or :title = '' or lower(ps.id.post.title) like lower(concat('%', :title, '%') ) )
                and(:companyName is null or :companyName = '' or lower(ps.id.post.company.name) like lower(concat('%', :companyName, '%') ) )
                and (
                        (:minPay is null and :maxPay is null)
                        or
                        (
                            (:minPay is not null and :maxPay is not null and  (ps.id.post.minPay between :minPay and :maxPay or ps.id.post.maxPay between :minPay and :maxPay))
                        )
                        or
                        (
                            (:maxPay is null and :minPay is not null and ps.id.post.minPay >= :minPay)
                        )
                        or
                        (
                            (:minPay is null and :maxPay is not null and ps.id.post.maxPay <= :maxPay)
                        )
                    )
                and (:skills is null or (:skills is not null and ps.id.skill.id in :skills))
                and ps.id.post.closeDate >= :now
                and ps.id.post.postStatus = :postStatus
                group by p, ps.id.post.id, ps.id.post.closeDate
""", countQuery = """
            select count (*)
                        from PostSkill ps
            join Post p on ps.id.post.id = p.id
            join Skill s on s.id = ps.id.skill.id
            where ps.id.post.deleted = false
                and (:experience is null or :experience = '' or lower(ps.id.post.experience) like lower(concat('%', :experience, '%') ) )
                and(:gender is null or :gender = '' or lower(ps.id.post.gender) like lower(concat('%', :gender, '%') ) )
                and(:jobType is null or :jobType = '' or lower(ps.id.post.jobType) like lower(concat('%', :jobType, '%')))
                and(:location is null or ps.id.post.location = :location)
                and(:rank is null or :rank = '' or lower(ps.id.post.rank) like lower(concat('%', :rank, '%') ) )
                and(:quantity is null or ps.id.post.quantity = :quantity)
                and(:title is null or :title = '' or lower(ps.id.post.title) like lower(concat('%', :title, '%') ) )
                and(:companyName is null or :companyName = '' or lower(ps.id.post.company.name) like lower(concat('%', :companyName, '%') ) )
                and (
                        (:minPay is null and :maxPay is null)
                        or
                        (
                            (:minPay is not null and :maxPay is not null and  (ps.id.post.minPay between :minPay and :maxPay or ps.id.post.maxPay between :minPay and :maxPay))
                        )
                        or
                        (
                            (:maxPay is null and :minPay is not null and ps.id.post.minPay >= :minPay)
                        )
                        or
                        (
                            (:minPay is null and :maxPay is not null and ps.id.post.maxPay <= :maxPay)
                        )
                    )
                and (:skills is null or (:skills is not null and ps.id.skill.id in :skills))
                and ps.id.post.closeDate >= :now
                and ps.id.post.postStatus = :postStatus
                group by p, ps.id.post.id, ps.id.post.closeDate
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
            @Param("now") LocalDate now,
            @Param("postStatus") PostStatus postStatus,
            @Param("skills") List<Integer> skills,
            Pageable pageable);
}
