package com.ismhac.jspace.dto.payment.request.custom;

import lombok.Data;

@Data
public class CustomParamV1 {
    // custom params
    private int companyId;
    private int productId;
    private int quantity;
}
