package com.ismhac.jspace.service.common.thirtParty.response;

import lombok.Data;

import java.util.List;

@Data
public class PayPalCreateOrderResponse {
    private String id;
    private String status;
    private PaymentSource payment_source;
    private List<Link> links;

    @Data
    public static class PaymentSource {
        private PayPal paypal;
    }

    @Data
    public static class PayPal {
        // This can be further expanded based on actual response structure
    }

    @Data
    public static class Link {
        private String href;
        private String rel;
        private String method;
    }
}