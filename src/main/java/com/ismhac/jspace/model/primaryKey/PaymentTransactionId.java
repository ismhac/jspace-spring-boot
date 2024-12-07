package com.ismhac.jspace.model.primaryKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PaymentTransactionId {
    @Column(name = "payment_id")
    private String paymentId;
    private String mac;
}
