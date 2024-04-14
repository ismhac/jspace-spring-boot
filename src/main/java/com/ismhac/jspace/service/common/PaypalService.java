package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.payment.request.PaymentCreateRequest;
import com.paypal.api.payments.Payment;

public interface PaypalService {
    Payment createPayment(PaymentCreateRequest paymentCreateRequest);
}
