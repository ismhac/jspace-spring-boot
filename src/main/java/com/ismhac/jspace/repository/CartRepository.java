package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("""
            select c from Cart c where c.company.id = ?1 and c.product.id = ?2
            """)
    Optional<Cart> findByCompanyIdAndProductId(int companyId, int productId);

    @Modifying
    @Query("""
            delete from Cart c where c.id = ?1
            """)
    int deleteById(int cartId);

    @Query("""
            select c from Cart c where c.company.id = ?1
            """)
    Page<Cart> getPageByCompanyId(int companyId, Pageable pageable);

    List<Cart> findAllByIdIsIn(List<Integer> ids);
}
