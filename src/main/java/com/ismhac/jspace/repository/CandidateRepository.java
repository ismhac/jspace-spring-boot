package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.enums.Experience;
import com.ismhac.jspace.model.enums.Gender;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.Rank;
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
            select cp.id.candidate
            from CandidateProfile cp
            where(:gender is null or (:gender is not null and cp.gender = :gender))
                and (:experience is null or (:experience is not null and cp.experience = :experience))
                and (:rank is null or (:rank is not null and cp.rank = :rank) )
                and(:location is null or (:location is not null and cp.location = :location))
                and cp.id.candidate.publicProfile = true
            """)
    Page<Candidate> recruiterSearchCandidate(Gender gender,
                                             Experience experience,
                                             Rank rank,
                                             Location location, Pageable pageable);
}
