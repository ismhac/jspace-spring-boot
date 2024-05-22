package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.PurchasedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedProductRepository extends JpaRepository<PurchasedProduct, Integer> {

}
