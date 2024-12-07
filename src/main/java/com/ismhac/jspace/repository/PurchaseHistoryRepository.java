package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.PurchaseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {

    @Query("""
            select ph from PurchaseHistory ph
            where (:companyName is null or :companyName = '' or lower(ph.company.name) like lower(concat('%', :companyName, '%')))
                and (:productName is null or :productName = '' or lower(ph.productName) like lower(concat('%', :productName, '%')))
            """)
    Page<PurchaseHistory> findAndFilter(String companyName, String productName, Pageable pageable);

    @Query("""
            select ph from PurchaseHistory ph where ph.company.id = ?1
                and(?2 is null or ?2 = '' or lower(ph.productName) like lower(concat('%', ?2, '%')))
            """)
    Page<PurchaseHistory> findByCompanyIdAndFilterByProductName(int companyId, String productName, Pageable pageable);
}
