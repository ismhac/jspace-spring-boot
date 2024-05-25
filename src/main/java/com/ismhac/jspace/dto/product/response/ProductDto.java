package com.ismhac.jspace.dto.product.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    int id;
    String name;
    int price;
    int numberOfPost;
    int postDuration;
    int durationDayNumber;
    String description;
}