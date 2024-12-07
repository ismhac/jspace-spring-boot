package com.ismhac.jspace.service.common.thirtParty.response;

import lombok.Data;

import java.util.List;

@Data
public class PayPalWebhookListResponse {
    private List<PayPalWebhook> webhooks;

    @Data
    public static class PayPalWebhook {
        private String id;
        private String url;
        private List<EventType> event_types;
        private List<Link> links;
    }

    @Data
    public static class EventType {
        private String name;
        private String description;
    }

    @Data
    public static class Link {
        private String href;
        private String rel;
        private String method;
    }
}