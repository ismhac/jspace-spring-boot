package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    @Query("""
            select t1 from Resume t1 where t1.candidate.id.user.id = :id
            """)
    Page<Resume> findAllByCandidateId(int id, Pageable pageable);
}
