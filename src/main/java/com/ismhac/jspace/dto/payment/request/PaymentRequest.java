package com.ismhac.jspace.dto.payment.request;

import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {
    private String intent; // Purpose of payment, usually "sale"
    private String paymentMethod; // Payment method, usually "paypal"
    private String currency; // Currency, example: "USD"
    private String total; // Total amount to be paid
    private String cancelUrl; // Redirect URL when payment fails
    private String successUrl; // Redirect URL when payment is successful
    private List<Integer> cartIds;
}
