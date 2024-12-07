package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.CandidateFollowCompany;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.primaryKey.CandidateFollowCompanyId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    @Query("""
            select cfp.id.candidate.id.user
            from CandidateFollowCompany cfp
            where cfp.id.company.id = :companyId
                and (:name is null or :name = '' or lower(cfp.id.candidate.id.user.name) like lower(concat('%', :name, '%') ) )
                and (:email is null or :email = '' or lower(cfp.id.candidate.id.user.email) like lower(concat('%', :email, '%') ) )
                and (:phoneNumber is null or :phoneNumber = '' or lower(cfp.id.candidate.id.user.phone) like lower(concat('%', :phoneNumber, '%') ) )
            """)
    Page<User> getPageUserFollowedCompanyByCompanyId(int companyId, String name, String email, String phoneNumber,Pageable pageable);

    @Query("""
            select cfp.id.candidate
            from CandidateFollowCompany cfp
            where cfp.id.company.id = :companyId
            """)
    List<Candidate> getListFollowedCompany(int companyId);
}
