package com.ismhac.jspace.service.common;

import com.ismhac.jspace.another.PayPalAuthService;
import lombok.extern.slf4j.Slf4j;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
@Slf4j
public class PaypalUtils {
    private static final String PAYPAL_URL = "https://api-m.sandbox.paypal.com/v1/notifications/webhooks";
    private final PayPalAuthService authService;

    public PaypalUtils(PayPalAuthService authService) {
        this.authService = authService;
    }

    public void registerWebhook() throws IOException {
        log.info("REGISTER WEBHOOK");
        String token = authService.getAccessToken();
        HttpURLConnection httpConn = createHttpConnection(PAYPAL_URL, "POST", token);
        sendRequest(httpConn, "{ \"url\": \"https://jspace.space/jspace-service/api/v1/payment/paypal-webhooks\", \"event_types\": [{ \"name\": \"*\" }] }");
        handleResponse(httpConn);
        log.info("REGISTER WEBHOOK");
    }

    private HttpURLConnection createHttpConnection(String url, String method, String token) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) urlObj.openConnection();
        httpConn.setRequestMethod(method);
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer " + token);
        httpConn.setDoOutput(true);
        return httpConn;
    }

    private void sendRequest(HttpURLConnection httpConn, String payload) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream())) {
            writer.write(payload);
            writer.flush();
        }
    }

    private void handleResponse(HttpURLConnection httpConn) throws IOException {
        try (InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream()) {
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            log.info(response);
        }
    }

    public void deleteAllWebhooks() throws IOException {
        log.info("DELETE WEBHOOK");
        String token = authService.getAccessToken();
        HttpURLConnection httpConn = createHttpConnection(PAYPAL_URL, "GET", token);

        String response = handleGetResponse(httpConn);
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray webhooks = jsonResponse.getJSONArray("webhooks");

        for (int i = 0; i < webhooks.length(); i++) {
            String webhookId = webhooks.getJSONObject(i).getString("id");
            deleteWebhook(webhookId, token);
        }
        log.info("DELETE WEBHOOK");
    }

    private String handleGetResponse(HttpURLConnection httpConn) throws IOException {
        try (InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream()) {
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }

    private void deleteWebhook(String webhookId, String token) throws IOException {
        String deleteUrl = PAYPAL_URL + "/" + webhookId;
        HttpURLConnection httpConn = createHttpConnection(deleteUrl, "DELETE", token);
        handleResponse(httpConn);
    }

    public void updateWebhook(String webhookId) throws IOException {
        String token = authService.getAccessToken();
        String updateUrl = PAYPAL_URL + "/" + webhookId;
        HttpURLConnection httpConn = createHttpConnection(updateUrl, "PATCH", token);
        sendRequest(httpConn, "[{ \"op\": \"replace\", \"path\": \"/event_types\", \"value\": [{ \"name\": \"*\" }] }]");
        handleResponse(httpConn);
    }
}

