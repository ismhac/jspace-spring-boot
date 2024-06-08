package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.CandidateFollowCompany;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.primaryKey.CandidateFollowCompanyId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CandidateFollowCompanyRepository extends JpaRepository<CandidateFollowCompany, CandidateFollowCompanyId> {

    @Query("""
            select cfp.id.company from CandidateFollowCompany cfp where cfp.id.candidate.id.user.id = :candidateId
            """)
    Page<Company> getPageFollowedCompaniesByCandidateId(int candidateId, Pageable pageable);

    @Modifying
    @Query("""
            delete from CandidateFollowCompany cfp where cfp.id.candidate.id.user.id = ?1 and cfp.id.company.id = ?2
            """)
    int deleteById(int candidateId, int companyId);

    @Query("""
            select coalesce(count(distinct (cfp.id.candidate.id.user.id)), 0) from CandidateFollowCompany cfp where cfp.id.company.id = ?1
            """)
    int countFollowerOfCompany(int companyId);
}
