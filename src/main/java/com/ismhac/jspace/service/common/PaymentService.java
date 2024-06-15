package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.payment.request.PaymentRequest;
import com.paypal.api.payments.Payment;

public interface PaymentService {
    Payment requestPayment(PaymentRequest request);

    Object handleResponse(String body);
}
