package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.dto.payment.request.PaymentCreateRequest;
import com.ismhac.jspace.service.common.PaypalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService {


    private final APIContext apiContext;

    @Override
    public Payment createPayment(PaymentCreateRequest paymentCreateRequest) {
        Payment createPayment;
        try {
            Amount amount = new Amount(
                    paymentCreateRequest.getCurrency(),
                    paymentCreateRequest.getTotal());

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);

            // Add custom parameters
            JSONObject customParams = new JSONObject();
            customParams.put("companyId", paymentCreateRequest.getCompanyId());
            customParams.put("productId", paymentCreateRequest.getProductId());
            transaction.setCustom(customParams.toString());

            Payment payment = getPayment(paymentCreateRequest, transaction);

            createPayment = payment.create(apiContext);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(String.valueOf(e.getDetails()));
        }
        return createPayment;
    }

    public static Payment getPayment(PaymentCreateRequest paymentCreateRequest, Transaction transaction) {
        List<Transaction> transactions = Arrays.asList(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paymentCreateRequest.getCancelUrl());
        redirectUrls.setReturnUrl(paymentCreateRequest.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);
        return payment;
    }
}
