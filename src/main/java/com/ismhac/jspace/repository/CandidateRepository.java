package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.primaryKey.CandidateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, CandidateId> {

    @Query("""
            select c from Candidate c where c.id.user.id = :id
            """)
    Optional<Candidate> findByUserId(int id);

    @Query("""
            select c from Candidate c where c.id.user.email = :email
            """)
    Optional<Candidate> findByUserEmail(String email);
}
