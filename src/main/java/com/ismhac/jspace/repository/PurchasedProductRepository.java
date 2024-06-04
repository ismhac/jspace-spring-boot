package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.PurchasedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PurchasedProductRepository extends JpaRepository<PurchasedProduct, Integer> {

    @Query("""
            select pd as purchasedProduct
            from PurchasedProduct pd
            where pd.company.id = :companyId
                and pd.expiryDate >= :now
            """)
    List<PurchasedProduct> getListByCompanyId(int companyId, LocalDate now);

    @Query("""
            select pp
            from PurchasedProduct pp
            where pp.company.id = ?1
                and(?2 is null or ?2 = '' or lower(pp.productName) like lower(concat('%', ?2, '%')))
            order by pp.createdAt desc
            """)
    Page<PurchasedProduct> getPageByCompanyId(int companyId, String productName, Pageable pageable);

    Optional<PurchasedProduct> findByIdAndCompanyId(int id, int companyId);
}
