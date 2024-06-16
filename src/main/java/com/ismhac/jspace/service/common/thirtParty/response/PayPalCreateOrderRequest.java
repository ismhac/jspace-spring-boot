package com.ismhac.jspace.service.common.thirtParty.response;

import lombok.Data;

import java.util.List;

@Data
public class PayPalCreateOrderRequest {
    private String intent;
    private List<PurchaseUnit> purchase_units;
    private PaymentSource payment_source;

    @Data
    public static class PurchaseUnit {
        private String reference_id;
        private Amount amount;
    }

    @Data
    public static class Amount {
        private String currency_code;
        private String value;
    }

    @Data
    public static class PaymentSource {
        private PayPal paypal;
    }

    @Data
    public static class PayPal {
        private ExperienceContext experience_context;
    }

    @Data
    public static class ExperienceContext {
        private String payment_method_preference;
        private String brand_name;
        private String locale;
        private String landing_page;
        private String shipping_preference;
        private String user_action;
        private String return_url;
        private String cancel_url;
    }
}
