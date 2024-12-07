package com.ismhac.jspace.controller.payment;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.service.common.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/check-payment")
    public ApiResponse<Object> checkPayment(
            @RequestParam("mac") final String mac,
            @RequestParam("paymentId") final String paymentId,
            @RequestParam("PayerID") final String payerId) {
        try {
            return ApiResponse.builder().result(paymentService.checkPayment(mac, paymentId, payerId)).build();
        } catch (Exception e) {
            return ApiResponse.builder().result(new HashMap<>() {{
                put("status", false);
            }}).build();
        }
    }
}
