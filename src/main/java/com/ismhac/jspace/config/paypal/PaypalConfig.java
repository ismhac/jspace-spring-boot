package com.ismhac.jspace.config.paypal;

import com.ismhac.jspace.another.PayPalAuthService;
import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String secret;

    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext() {
        return new APIContext(clientId, secret, mode);
    }

    @Bean
    public PayPalAuthService payPalAuthService() {
        return new PayPalAuthService(clientId, secret, mode);
    }
}
