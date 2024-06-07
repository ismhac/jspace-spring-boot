package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    @Query("""
            select s from Skill s where s.id in ?1
            """)
    List<Skill> findAllByIdList(List<Integer> idList);
}
