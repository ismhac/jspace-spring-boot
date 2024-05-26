package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("""
            select 
                p as post,
                (
                    case
                        when
                            exists (
                                select 1
                                from CandidatePostLiked cpl
                                where cpl.id.post.id = p.id
                                    and cpl.id.candidate.id.user.id = :candidateId
                            )
                        then true else false  
                    end 
                ) as liked,
                (
                    case
                        when
                            exists (
                                select 1
                                from CandidatePost cp
                                where cp.id.post.id = p.id
                                    and cp.id.candidate.id.user.id = :candidateId
                            )
                        then true else false
                    end
                ) as applied
            from Post p
            """)
    Page<Map<String, Object>> candidateGetPagePost(int candidateId, Pageable pageable);

    @Query("""
            select p
            from Post p
            where p.company.id = :companyId
            """)
    Page<Post> getPageByCompanyId(int companyId, Pageable pageable);
}
