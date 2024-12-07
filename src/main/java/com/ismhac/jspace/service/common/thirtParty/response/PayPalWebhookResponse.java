package com.ismhac.jspace.service.common.thirtParty.response;

import com.paypal.api.payments.EventType;
import lombok.Data;

import java.util.List;

@Data
public class PayPalWebhookResponse {
    private String id;
    private String url;
    private List<EventType> event_types;
    private List<Link> links;

    @Data
    public static class Link {
        private String href;
        private String rel;
        private String method;
    }

    @Data
    public static class EventType {
        private String name;
        private String description;
    }
}
