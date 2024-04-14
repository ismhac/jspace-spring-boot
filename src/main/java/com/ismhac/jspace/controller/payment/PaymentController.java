package com.ismhac.jspace.controller.payment;

import com.ismhac.jspace.dto.payment.request.PaymentCreateRequest;
import com.ismhac.jspace.service.common.PaypalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaypalService paypalService;

    @PostMapping("/request-payment")
    public String requestPayment(@RequestBody PaymentCreateRequest paymentCreateRequest) {
        var result = paypalService.createPayment(paymentCreateRequest);
        log.info("--- result: {}", result.toString());
        return result.toJSON();
    }
}
