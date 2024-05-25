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

    /*
    * SELECT
	tp.id,
	tp.title,
	(CASE WHEN EXISTS(
				SELECT 1
				FROM tbl_candidate_post_liked tcpl
				WHERE tcpl.post_id = tp.id
					AND tcpl.candidate_id = 2)
			THEN true ELSE FALSE END ) AS liked,
	(CASE WHEN EXISTS (
				SELECT 1
				FROM tbl_candidate_post tcp
				WHERE tcp.post_id = tp.id
					AND tcp.candidate_id = 2)
			THEN TRUE ELSE FALSE END) AS applied
FROM tbl_post tp
    * */
}
