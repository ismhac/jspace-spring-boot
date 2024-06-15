//package com.ismhac.jspace.another;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Scanner;
//
//@Service
//public class PayPalWebhookService {
//    private static final String PAYPAL_URL = "https://api-m.sandbox.paypal.com/v1/notifications/webhooks";
////    @Value("${paypal.client.id}")
//    private final String clientId = "AZAWI1-cCJaWmy1_DAjMsX0PTs9RcfFXNGepLPrFmh-MYf_31VXVSvhklemGkNmzWk33SzxGXx9gC__Z";
////    @Value("${paypal.client.secret}")
//    private final String clientSecret = "EDyoufKa2JKLdv4NQJBXDqhqdFxeo48rUtbOyoIKec-ZoOnwUAcQEIngmSSU302HlP0ZSDPaA1ZchEiv";
//    private final PayPalAuthService authService = new PayPalAuthService(clientId, clientSecret);
//
//    public PayPalWebhookService() {}
//
//    public void registerWebhook() throws IOException {
//        String token = authService.getAccessToken();
//
//        URL url = new URL("https://api-m.sandbox.paypal.com/v1/notifications/webhooks");
//        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//        httpConn.setRequestMethod("POST");
//
//        httpConn.setRequestProperty("Content-Type", "application/json");
//        httpConn.setRequestProperty("Authorization", "Bearer " + token);
//
//        httpConn.setDoOutput(true);
//        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
//        writer.write("{ \"url\": \"https://jspace.space/jspace-service/api/v1/payment/paypal-webhooks\", \"event_types\": ["
//                + "{ \"name\": \"PAYMENT.AUTHORIZATION.CREATED\" },"
//                + "{ \"name\": \"PAYMENT.AUTHORIZATION.VOIDED\" },"
//                + "{ \"name\": \"PAYMENT.CAPTURE.COMPLETED\" },"
//                + "{ \"name\": \"PAYMENT.CAPTURE.DENIED\" },"
//                + "{ \"name\": \"PAYMENT.CAPTURE.PENDING\" },"
//                + "{ \"name\": \"PAYMENT.CAPTURE.REFUNDED\" },"
//                + "{ \"name\": \"PAYMENT.CAPTURE.REVERSED\" },"
//                + "{ \"name\": \"PAYMENT.SALE.COMPLETED\" },"
//                + "{ \"name\": \"PAYMENT.SALE.DENIED\" },"
//                + "{ \"name\": \"PAYMENT.SALE.PENDING\" },"
//                + "{ \"name\": \"PAYMENT.SALE.REFUNDED\" },"
//                + "{ \"name\": \"PAYMENT.SALE.REVERSED\" }"
//                + "] }");
//        writer.flush();
//        writer.close();
//        httpConn.getOutputStream().close();
//
//        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
//                ? httpConn.getInputStream()
//                : httpConn.getErrorStream();
//        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
//        String response = s.hasNext() ? s.next() : "";
//        System.out.println(response);
//    }
//
//
//    public void deleteAllWebhooks() throws IOException {
//        String token = authService.getAccessToken();
//
//        URL url = new URL(PAYPAL_URL);
//        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//        httpConn.setRequestMethod("GET");
//        httpConn.setRequestProperty("Authorization", "Bearer " + token);
//
//        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
//                ? httpConn.getInputStream()
//                : httpConn.getErrorStream();
//        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
//        String response = s.hasNext() ? s.next() : "";
//
//        // Parse the response to get webhook IDs
//        JsonParser parser = new JsonParser();
//        JsonObject jsonResponse = parser.parse(response).getAsJsonObject();
//        JsonArray webhooksArray = jsonResponse.getAsJsonArray("webhooks");
//
//        // Iterate through each webhook and delete it
//        for (JsonElement webhookElement : webhooksArray) {
//            JsonObject webhook = webhookElement.getAsJsonObject();
//            String webhookId = webhook.get("id").getAsString(); // Assuming 'id' is the key for webhook ID
//            deleteWebhook(webhookId, token);
//        }
//    }
//
//    private void deleteWebhook(String webhookId, String accessToken) throws IOException {
//        URL url = new URL(PAYPAL_URL + "/" + webhookId);
//        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//        httpConn.setRequestMethod("DELETE");
//        httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
//
//        int responseCode = httpConn.getResponseCode();
//        if (responseCode >= 200 && responseCode < 300) {
//            System.out.println("Webhook " + webhookId + " deleted successfully.");
//        } else {
//            System.err.println("Failed to delete webhook " + webhookId + ". Response code: " + responseCode);
//            InputStream errorStream = httpConn.getErrorStream();
//            if (errorStream != null) {
//                Scanner errorScanner = new Scanner(errorStream).useDelimiter("\\A");
//                String errorMessage = errorScanner.hasNext() ? errorScanner.next() : "";
//                System.err.println("Error response: " + errorMessage);
//            }
//        }
//    }
//
//}
