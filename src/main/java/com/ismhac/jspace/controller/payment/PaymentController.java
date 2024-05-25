package com.ismhac.jspace.controller.payment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.payment.request.PaymentCreateRequest;
import com.ismhac.jspace.service.common.PaypalService;
import com.paypal.api.payments.Webhook;
import com.paypal.api.payments.WebhookList;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Payment")
public class PaymentController {

    private final PaypalService paypalService;

    @PostMapping("/request-payment")
    public String requestPayment(@RequestBody PaymentCreateRequest paymentCreateRequest) {
        var result = paypalService.createPayment(paymentCreateRequest);
//        log.info("--- result: {}", result.toString());
        return result.toJSON();
    }

    @Hidden()
    @PostMapping("/paypal-webhooks") // "Listen action payment, callback method"
    public ResponseEntity<Void> listenActionPaymentCompleted(@RequestBody String body) {
        //        log.info(String.format("------Body input: %s", request));
        var result = paypalService.listenPaypalWebhooks(body);
        log.info("result: {}", result.toString());
        return ResponseEntity.ok().build();
    }

    @Hidden()
    @PostMapping("/paypal-webhooks/simulate")
    public ResponseEntity<Void> simulateListenActionPaymentCompleted(@RequestBody String body) {
//        log.info(String.format("------Body input: %s", request));
        var result = paypalService.listenPaypalWebhooks(body);
        log.info("result: {}", result.toString());
        return ResponseEntity.ok().build();
    }

}
