package com.ismhac.jspace.controller.payment;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.payment.request.PaymentRequest;
import com.ismhac.jspace.service.common.PaypalUtils;
import com.ismhac.jspace.service.common.thirtParty.PaypalServiceV2;
import com.ismhac.jspace.service.common.thirtParty.response.PayPalAccessTokenResponse;
import com.ismhac.jspace.service.common.thirtParty.response.PayPalWebhookListResponse;
import com.ismhac.jspace.service.common.thirtParty.response.PayPalWebhookResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Hidden
@RestController
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
@Tag(name = "Paypal")
public class PaypalController {
    private final PaypalServiceV2 paypalServiceV2;
    private final PaypalUtils paypalUtils;

    @GetMapping("/authentication")
    public ApiResponse<PayPalAccessTokenResponse> authenticate() throws IOException {
        return ApiResponse.<PayPalAccessTokenResponse>builder()
                .result(paypalServiceV2.authenticate())
                .build();
    }

    @PostMapping("/register-webhooks")
    public ApiResponse<PayPalWebhookResponse> registerWebhook() throws IOException {
        return ApiResponse.<PayPalWebhookResponse>builder()
                .result(paypalServiceV2.registerWebhook("https://jspace.space/api/v1/payment/paypal-webhooks"))
                .build();
    }

    @GetMapping("/details-webhooks")
    public ApiResponse<PayPalWebhookResponse> getWebhookDetails(@RequestParam("webhookId") String webhookId) throws IOException{
        return ApiResponse.<PayPalWebhookResponse>builder()
                .result(paypalServiceV2.getWebhookDetails(webhookId))
                .build();
    }

    @GetMapping("/list-webhooks")
    public ApiResponse<PayPalWebhookListResponse> getListOfWebhooks() throws IOException {
        return ApiResponse.<PayPalWebhookListResponse>builder()
                .result(paypalServiceV2.getListOfWebhooks())
                .build();
    }

    @PostMapping("/create-order")
    public String requestPayment(@RequestBody PaymentRequest request) {
        var result = paypalServiceV2.requestPayment(request);
        return result.toJSON();
    }

    @GetMapping("/delete-all-webhooks")
    public ResponseEntity<Void> deleteAllWebhooks() throws IOException {
        paypalUtils.deleteAllWebhooks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
