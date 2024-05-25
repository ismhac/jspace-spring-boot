package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.PurchasedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PurchasedProductRepository extends JpaRepository<PurchasedProduct, Integer> {

    @Query("""
            select pd as purchasedProduct
            from PurchasedProduct pd
            where pd.company.id = :companyId
                and pd.expiryDate >= :now
            """)
    List<PurchasedProduct> getListByCompanyId(int companyId, LocalDate now);
}
