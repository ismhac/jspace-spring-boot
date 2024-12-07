package com.ismhac.jspace.dto.product.response;

import com.ismhac.jspace.dto.other.ProductTypeDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto implements Serializable {
    int id;
    String name;
    int price;
    int numberOfPost;
    int postDuration;
    int durationDayNumber;
    String description;
    Boolean deleted;
    ProductTypeDto productType;
}