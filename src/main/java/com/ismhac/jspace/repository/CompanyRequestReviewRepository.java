package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.CompanyRequestReview;
import com.ismhac.jspace.model.primaryKey.CompanyRequestReviewId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRequestReviewRepository extends
        JpaRepository<CompanyRequestReview, CompanyRequestReviewId> {

    @Query("""
            select crr
            from CompanyRequestReview crr
            where (:reviewed is null or crr.reviewed = :reviewed)
                and(:name is null or :name = '' or lower(crr.id.company.name) like lower(concat('%', :name, '%') ) )
                and(:address is null or :address = '' or lower(crr.id.company.address) like lower(concat('%', :address, '%') ) )
                and(:email is null or :email = '' or lower(crr.id.company.email) like lower(concat('%', :email, '%') ) )
                and(:phone is null or :phone = '' or lower(crr.id.company.phone) like lower(concat('%', :phone, '%') ) )
            """)
    Page<CompanyRequestReview> getPageFilterByReviewed(String name, String address, String email, String phone,Boolean reviewed, Pageable pageable);

    @Query("""
            select crr from CompanyRequestReview crr where crr.id.company.id = :companyId
            """)
    Optional<CompanyRequestReview> findByCompanyId(int companyId);
}
