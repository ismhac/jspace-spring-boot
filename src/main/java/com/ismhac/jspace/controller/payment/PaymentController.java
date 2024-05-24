package com.ismhac.jspace.controller.payment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    public ResponseEntity<Void> listenActionPaymentCompleted(@RequestBody String request) {
        log.info(String.format("------Body input: %s", request));
        return ResponseEntity.ok().build();
    }

    @Hidden()
    @PostMapping("/paypal-webhooks/simulate")
    public ResponseEntity<Void> simulateListenActionPaymentCompleted(@RequestBody String request) {
//        log.info(String.format("------Body input: %s", request));

        Gson gson = new Gson();

        Map<String, Object> body = gson.fromJson(request, new TypeToken<Map<String, Object>>(){}.getType());

        Map<String, Object> resource = (Map<String, Object>) body.get("resource");

        List<Map<String, Object>> transactions = (List<Map<String, Object>>) resource.get("transactions");

        Map<String, Object> payer = (Map<String, Object>) resource.get("payer");

        String paymentMethod = (String) payer.get("payment_method");
        String status = (String) payer.get("status");

        Map<String, Object> amount = (Map<String, Object>) transactions.get(0).get("amount");

        String total = (String) amount.get("total");

        String custom = (String) transactions.get(0).get("custom");

        Map<String, Object> customObj = gson.fromJson(custom, new TypeToken<Map<String, Object>>(){}.getType());

        int companyId = ((Double) customObj.get("companyId")).intValue();
        int productId = ((Double) customObj.get("productId")).intValue();

        return ResponseEntity.ok().build();
    }

}
