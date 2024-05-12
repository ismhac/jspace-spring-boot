package com.ismhac.jspace.dto.product.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    String name;
    int price;
    int numberOfPost;
    int postDuration;
    int durationDayNumber;
    String description;
}
