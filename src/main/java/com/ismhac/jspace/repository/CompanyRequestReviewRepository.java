package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.CompanyRequestReview;
import com.ismhac.jspace.model.primaryKey.CompanyRequestReviewId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRequestReviewRepository extends
        JpaRepository<CompanyRequestReview, CompanyRequestReviewId> {
}
