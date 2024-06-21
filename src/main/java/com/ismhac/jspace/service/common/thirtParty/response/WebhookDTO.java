package com.ismhac.jspace.service.common.thirtParty.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WebhookDTO {
    private String id;
    private String url;
    private List<EventType> eventTypes;
    private List<Link> links;

    // Constructor, getters, and setters
    public WebhookDTO() {
    }

    public WebhookDTO(String id, String url, List<EventType> eventTypes, List<Link> links) {
        this.id = id;
        this.url = url;
        this.eventTypes = eventTypes;
        this.links = links;
    }

    // Inner classes for EventType and Link
    @Setter
    @Getter
    public static class EventType {
        private String name;
        private String description;
        private String status;

        // Constructor, getters, and setters
        public EventType() {
        }

        public EventType(String name, String description, String status) {
            this.name = name;
            this.description = description;
            this.status = status;
        }

    }

    @Setter
    @Getter
    public static class Link {
        private String href;
        private String rel;
        private String method;

        // Constructor, getters, and setters
        public Link() {
        }

        public Link(String href, String rel, String method) {
            this.href = href;
            this.rel = rel;
            this.method = method;
        }

    }
}

