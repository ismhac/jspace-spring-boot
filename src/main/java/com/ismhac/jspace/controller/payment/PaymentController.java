package com.ismhac.jspace.controller.payment;

import com.ismhac.jspace.service.common.PaymentService;
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
    @Hidden()
    @PostMapping("/paypal-webhooks")
    public ResponseEntity<Void> listenActionPaymentCompleted(@RequestBody String body) {
        log.info(String.format("------Body input: %s", body));
        var result = paymentService.handleResponse(body);
        log.info("--- Check webhook process: {}", result.toString());
        return ResponseEntity.ok().build();
    }
}
