package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.PaymentTransactionId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_payment_transaction")
public class PaymentTransaction extends BaseEntity{
    @EmbeddedId
    private PaymentTransactionId id;
    @Column(name = "cart_value")
    private String cartValue;
}
