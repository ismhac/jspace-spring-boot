package com.ismhac.jspace.dto.other;

import com.ismhac.jspace.model.enums.ProductType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductTypeDto {
    private ProductType productType;
    private String description;
}
