package com.ismhac.jspace.controller.payment;

import com.ismhac.jspace.dto.payment.request.PaymentCreateRequest;
import com.ismhac.jspace.service.common.PaypalService;
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
        log.info("--- result: {}", result.toString());
        return result.toJSON();
    }


    @Hidden()
    @PostMapping(value = "/paypal-webhooks", // "Listen action payment, callback method"
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> listenActionPaymentCompleted(HttpServletRequest request) {
        Map<String, String[]> requestBodyMap = request.getParameterMap();
        Map<String, String> body = new HashMap<>();
        requestBodyMap.forEach((key, values) -> body.put(key, values[0]));
        log.info(String.format("------Body input: %s", body));
        return ResponseEntity.ok().build();
    }

}
