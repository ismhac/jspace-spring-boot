package com.ismhac.jspace.another;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

public class PayPalAuthService {
    private static final String PAYPAL_TOKEN_URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
    private final String clientId;
    private final String clientSecret;
    private final String mode;

    public PayPalAuthService(String clientId, String clientSecret, String mode) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.mode = mode;
    }

    public String getAccessToken() throws IOException {
        URL url = new URL(PAYPAL_TOKEN_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        // Set the Authorization header
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        httpConn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Enable output stream to send the POST body
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("grant_type=client_credentials");
        writer.flush();
        writer.close();

        // Get the response from the server
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        responseStream.close();

        System.out.println("Response from PayPal: " + response); // Debugging line

        // Parse the response to extract the access token
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);

        if (jsonNode.has("access_token")) {
            return jsonNode.get("access_token").asText();
        } else {
            throw new IOException("Failed to retrieve access token: " + jsonNode.toString());
        }
    }
}
