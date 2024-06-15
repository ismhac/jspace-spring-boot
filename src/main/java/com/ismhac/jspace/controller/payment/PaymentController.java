package com.ismhac.jspace.controller.payment;

import com.ismhac.jspace.dto.payment.request.PaymentCreateRequest;
import com.ismhac.jspace.dto.payment.request.PaymentCreateRequestV2;
import com.ismhac.jspace.dto.payment.request.PaymentRequest;
import com.ismhac.jspace.service.common.PaymentService;
import com.ismhac.jspace.service.common.PaypalService;
import com.ismhac.jspace.service.common.PaypalUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/request-payment-v2")
    public String requestPaymentV2(@RequestBody PaymentRequest request) {
        var result = paymentService.requestPayment(request);
        log.info("--- result: {}", result.toString());
        return result.toJSON();
    }

    @Hidden()
    @PostMapping("/paypal-webhooks") // "Listen action payment, callback method"
    public ResponseEntity<Void> listenActionPaymentCompleted(@RequestBody String body) {
        log.info(String.format("------Body input: %s", body));
        var result = paymentService.handleResponse(body);
        log.info("result: {}", result.toString());
        return ResponseEntity.ok().build();
    }
}
