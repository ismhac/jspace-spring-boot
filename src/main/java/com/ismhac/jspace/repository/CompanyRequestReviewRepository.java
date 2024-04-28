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
            """)
    Page<CompanyRequestReview> getPageFilterByReviewed(Boolean reviewed, Pageable pageable);

    @Query("""
            select crr
            from CompanyRequestReview crr
            where crr.id.company.id = :companyId
            """)
    Optional<CompanyRequestReview> findByCompanyId(int companyId);
}
