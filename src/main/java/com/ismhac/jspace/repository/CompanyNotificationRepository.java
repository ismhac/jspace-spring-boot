package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.CompanyNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyNotificationRepository extends JpaRepository<CompanyNotification, Long> {
    @Query("select c from CompanyNotification c where c.company.id = ?1 order by c.createdAt DESC")
    Page<CompanyNotification> findByCompanyIdOrderByCreatedAtDesc(int companyId, Pageable pageable);
}
