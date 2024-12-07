package com.ismhac.jspace.service.common.thirtParty;

import com.google.gson.Gson;
import com.ismhac.jspace.dto.payment.request.PaymentRequest;

import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.model.Cart;
import com.ismhac.jspace.model.PaymentTransaction;
import com.ismhac.jspace.model.primaryKey.PaymentTransactionId;
import com.ismhac.jspace.repository.CartRepository;
import com.ismhac.jspace.repository.PaymentTransactionRepository;
import com.ismhac.jspace.service.common.thirtParty.response.PayPalAccessTokenResponse;
import com.ismhac.jspace.service.common.thirtParty.response.PayPalWebhookListResponse;
import com.ismhac.jspace.service.common.thirtParty.response.PayPalWebhookResponse;
import com.ismhac.jspace.util.HashUtils;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaypalServiceV2 {
    private static final String PAYPAL_API_BASE_URL = "https://api-m.sandbox.paypal.com";
    private final APIContext apiContext;

    private final CartRepository cartRepository;
    private final HashUtils hashUtils;
    private final PaymentTransactionRepository paymentTransactionRepository;

    public PayPalAccessTokenResponse authenticate() throws IOException {
        String authUrl = PAYPAL_API_BASE_URL + "/v1/oauth2/token";

        String authString = apiContext.getClientID() + ":" + apiContext.getClientSecret();
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(authUrl);

        httpPost.setHeader("Authorization", "Basic " + encodedAuthString);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        StringEntity params = new StringEntity("grant_type=client_credentials");
        httpPost.setEntity(params);

        CloseableHttpResponse response = httpClient.execute(httpPost);

        try {
            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Mapping JSON response to PayPalAccessTokenResponse object
            PayPalAccessTokenResponse accessTokenResponse = new PayPalAccessTokenResponse();
            accessTokenResponse.setScope(jsonResponse.getString("scope"));
            accessTokenResponse.setAccess_token(jsonResponse.getString("access_token"));
            accessTokenResponse.setToken_type(jsonResponse.getString("token_type"));
            accessTokenResponse.setApp_id(jsonResponse.getString("app_id"));
            accessTokenResponse.setExpires_in(jsonResponse.getLong("expires_in"));
            accessTokenResponse.setNonce(jsonResponse.getString("nonce"));

            return accessTokenResponse;
        } finally {
            response.close();
            httpClient.close();
        }
    }

    public PayPalWebhookResponse registerWebhook(String webhookUrl) throws IOException {
        String registerWebhookUrl = PAYPAL_API_BASE_URL + "/v1/notifications/webhooks";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(registerWebhookUrl);

        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + this.authenticate().getAccess_token());

        JSONObject requestBody = new JSONObject();
        requestBody.put("url", webhookUrl);

        JSONObject allEventTypes = new JSONObject();
        allEventTypes.put("name", "*");

        JSONArray eventTypesArray = new JSONArray();
        eventTypesArray.put(allEventTypes);
        requestBody.put("event_types", eventTypesArray);

        JSONObject resendConfig = new JSONObject();
        resendConfig.put("url", webhookUrl);

        JSONArray webhookEventTypesArray = new JSONArray();
        webhookEventTypesArray.put(resendConfig);
        requestBody.put("webhook_event_types", webhookEventTypesArray);

        StringEntity params = new StringEntity(requestBody.toString());
        httpPost.setEntity(params);

        CloseableHttpResponse response = httpClient.execute(httpPost);

        try {
            String responseBody = EntityUtils.toString(response.getEntity());
            Gson gson = new Gson();
            PayPalWebhookResponse webhookResponse = gson.fromJson(responseBody, PayPalWebhookResponse.class);

            return webhookResponse;
        } finally {
            response.close();
            httpClient.close();
        }
    }

    public PayPalWebhookResponse getWebhookDetails(String webhookId) throws IOException {
        String webhookDetailsUrl = PAYPAL_API_BASE_URL + "/v1/notifications/webhooks/" + webhookId;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(webhookDetailsUrl);

        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", "Bearer " + this.authenticate().getAccess_token());

        CloseableHttpResponse response = httpClient.execute(httpGet);

        try {
            String responseBody = EntityUtils.toString(response.getEntity());
            Gson gson = new Gson();
            PayPalWebhookResponse webhookResponse = gson.fromJson(responseBody, PayPalWebhookResponse.class);

            return webhookResponse;
        } finally {
            response.close();
            httpClient.close();
        }
    }

    public PayPalWebhookListResponse getListOfWebhooks() throws IOException {
        String webhooksUrl = PAYPAL_API_BASE_URL + "/v1/notifications/webhooks";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(webhooksUrl);

        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", "Bearer " + this.authenticate().getAccess_token());

        CloseableHttpResponse response = httpClient.execute(httpGet);

        try {
            String responseBody = EntityUtils.toString(response.getEntity());
            Gson gson = new Gson();
            PayPalWebhookListResponse webhookListResponse = gson.fromJson(responseBody, PayPalWebhookListResponse.class);

            return webhookListResponse;
        } finally {
            response.close();
            httpClient.close();
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public Payment requestPayment(PaymentRequest request) {
        Payment createdPayment;
        String mac = RandomStringUtils.randomAlphabetic(20);
        try {
            Transaction transaction = createTransaction(request);
            Payment payment = getPayment(request, transaction, mac);
            createdPayment = payment.create(apiContext);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(String.valueOf(e.getDetails()));
        }

        // custom
        List<Cart> carts = cartRepository.findAllByIdIsIn(request.getCart_ids());
        if(carts.size() != request.getCart_ids().size()){
            throw new AppException(ErrorCode.INVALID_CART);
        }

        String cartValue = request.getCart_ids().toString();

        String paymentId = createdPayment.getId();

        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .id(PaymentTransactionId.builder()
                        .paymentId(paymentId)
                        .mac(mac)
                        .build())
                .cartValue(cartValue)
                .build();

        paymentTransactionRepository.save(paymentTransaction);

        return createdPayment;
    }

    private Transaction createTransaction(PaymentRequest request) {
        Amount amount = new Amount(request.getCurrency(), request.getTotal());
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        return transaction;
    }


    private Payment getPayment(PaymentRequest request, Transaction transaction, String mac) {
        List<Transaction> transactions = Collections.singletonList(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(request.getPaymentMethod());

        Payment payment = new Payment();
        payment.setIntent(request.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(request.getCancelUrl().concat("?mac=").concat(mac));
        redirectUrls.setReturnUrl(request.getSuccessUrl().concat("?mac=").concat(mac));
        payment.setRedirectUrls(redirectUrls);

        return payment;
    }
}
