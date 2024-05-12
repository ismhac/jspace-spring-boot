package com.ismhac.jspace.dto.product.response;

import com.ismhac.jspace.dto.common.response.BaseEntityDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto extends BaseEntityDto implements Serializable {
    int id;
    String name;
    int price;
    int numberOfPost;
    int postDuration;
    int durationDayNumber;
    String description;
}