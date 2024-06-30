package com.ismhac.jspace.dto.payment.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @JsonIgnore
    @Builder.Default
    private String intent = "sale"; // Purpose of payment, usually "sale"
    @JsonIgnore
    @Builder.Default
    private String paymentMethod = "paypal"; // Payment method, usually "paypal"
    @JsonIgnore
    @Builder.Default
    private String currency = "USD"; // Currency, example: "USD"
    private String total; // Total amount to be paid
    private String cancelUrl; // Redirect URL when payment fails
    private String successUrl; // Redirect URL when payment is successful

    private List<Integer> cart_ids; // custom;
}
