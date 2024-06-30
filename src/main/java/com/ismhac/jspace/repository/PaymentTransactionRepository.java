package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.PaymentTransaction;
import com.ismhac.jspace.model.primaryKey.PaymentTransactionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, PaymentTransactionId> {
}
