package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.payment.request.PaymentCreateRequest;
import com.ismhac.jspace.dto.payment.request.PaymentCreateRequestV2;
import com.paypal.api.payments.Payment;

public interface PaypalService {
    Payment createPayment(PaymentCreateRequest paymentCreateRequest);

    Object listenPaypalWebhooks(String body);

    Object listenPaypalWebhooksV2(String body);

    Payment createPaymentV2(PaymentCreateRequestV2 paymentCreateRequestV2);
}
