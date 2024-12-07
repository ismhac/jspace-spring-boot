package com.ismhac.jspace.dto.product.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    String name;
    double price;
    int numberOfPost;
    int postDuration;
    int durationDayNumber;
    String description;
}
