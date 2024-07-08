package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.primaryKey.CandidateId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
            select c
            from Candidate c
            where(:name is null or :name = '' or lower(c.id.user.name) like lower(concat('%', :name, '%') ) )
                and (:email is null or :email = '' or lower(c.id.user.email) like lower(concat('%', :email, '%') ) )
                and (:phoneNumber is null or :phoneNumber = '' or lower(c.id.user.phone) like lower(concat('%', :phoneNumber, '%') ) )
                and c.publicProfile = true
            """)
    Page<Candidate> recruiterSearchCandidate(String name, String email, String phoneNumber, Pageable pageable);
}
