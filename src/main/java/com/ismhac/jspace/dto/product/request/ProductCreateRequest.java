package com.ismhac.jspace.dto.product.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequest {
    String name;
    double price;
    int numberOfPost;
    int postDuration;
    int durationDayNumber;
    String description;
}
