package com.ismhac.jspace.repository;

import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.model.PostSkill;
import com.ismhac.jspace.model.primaryKey.PostSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostSkillRepository extends JpaRepository<PostSkill, PostSkillId> {

    @Query("""
            select new com.ismhac.jspace.dto.skill.response.SkillDto(
                ps.id.skill.id,
                ps.id.skill.name
            )
            from PostSkill ps
            where ps.id.post.id = ?1
            """)
    List<SkillDto> findAllSkillsByPostId(int postId);
}
