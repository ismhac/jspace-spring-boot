package com.ismhac.jspace.service.common;

import com.paypal.base.rest.PayPalRESTException;

public interface PaymentService {
    Object checkPayment(String mac, String paymentId, String payerId) throws PayPalRESTException;
}
