package com.ismhac.jspace.dto.cart.request;

import lombok.Data;

@Data
public class CartCreateRequest {
    int companyId;
    int productId;
    int quantity;
}
