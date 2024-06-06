package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("""
            select company from Company company
            where company.activateStatus = true and (:name is null or :name = '' or lower(company.name) like lower(concat('%', :name, '%')))
                and (:address is null or :address = '' or lower(company.address) like lower(concat('%', :address, '%')))
            """)
    Page<Company> getPage(@Param("name") String name, @Param("address") String address, Pageable pageable);

    @Query("""
            select company from Company company where company.email = :email or company.phone = :phone
            """)
    Optional<Company> findByEmailOrPhone(String email, String phone);

    @Query("""
            select company from Company company
            where (:name is null or :name = '' or lower(company.name) like lower(concat('%', :name, '%') ) )
                and (:address is null or :address = '' or lower(company.address) like lower(concat('%', :address, '%') ) )
                and (:email is null or :email = '' or lower(company.email) like lower(concat('%', :email, '%') ) )
                and (:phone is null or :phone = '' or lower(company.phone) like lower(concat('%', :phone, '%') ) )
                and (:emailVerified is null or company.emailVerified = :emailVerified)
                and (:verifiedByAdmin is null or company.verifiedByAdmin = :verifiedByAdmin)
            """)
    Page<Company> getPageAndFilter(String name, String address, String email, String phone, Boolean emailVerified, Boolean verifiedByAdmin, Pageable pageable);

    @Query("""
            select 
                (case when :candidateId is null then 'guest' else 'candidate' end) as userMode,
               (case when :candidateId is null then null else (case when exists (select 1 from CandidateFollowCompany cfp where cfp.id.company.id = c.id and cfp.id.candidate.id.user.id = :candidateId) then true else false end) end) as followed,
                c as company
            from Company c
            where (:name is null or :name = '' or lower(c.name) like lower(concat('%', :name , '%')))
                and (:address is null or :address = '' or lower(c.address) like lower(concat('%', :address, '%')))
                and (:email is null or :email = '' or lower(c.email) like lower(concat('%', :email, '%')))
                and (:phone is null or :phone = '' or lower(c.phone) like lower(concat('%', :phone, '%')))
                and (:companySize is null or :companySize = '' or lower(c.companySize) like lower(concat('%', :companySize, '%')))
            """)
    Page<Map<String, Object>> findAllAndFilter(String name, String address, String email, String phone, String companySize, Integer candidateId, Pageable pageable);

    @Query("""
            select (case when ?2 is null then 'guest' else 'candidate' end) as userMode,
               (case when ?2 is null then null else (case when cfp.id.candidate.id.user.id is not null then true else false end) end) as followed,
                c as company
            from Company c
            left join CandidateFollowCompany cfp on cfp.id.company.id = c.id
            where c.id = ?1
            """)
    Map<String, Object> findByIdAndOptionalCandidateId(int companyId, Integer candidateId);
}
