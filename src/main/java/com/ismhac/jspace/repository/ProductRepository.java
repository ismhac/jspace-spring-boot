package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
            select product from Product product where product.deleted = false 
            and(:name is null or :name = '' or lower(product.name) like lower(concat('%', :name, '%') ) )
            """)
    Page<Product> getPage(String name,Pageable pageable);
}