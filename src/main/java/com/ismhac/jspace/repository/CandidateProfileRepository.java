package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.CandidateProfile;
import com.ismhac.jspace.model.primaryKey.CandidateProfileId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, CandidateProfileId> {
    Optional<CandidateProfile> findCandidateProfileById_Candidate_Id_User_Id(int candidateId);
}
