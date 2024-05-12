package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}