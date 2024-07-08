package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
            select product from Product product where product.deleted = false
            """)
    Page<Product> getPage(Pageable pageable);
}