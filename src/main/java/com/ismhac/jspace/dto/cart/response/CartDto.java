package com.ismhac.jspace.dto.cart.response;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.product.response.ProductDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {
    int id;
    CompanyDto company;
    ProductDto product;
    int quantity;
    Double totalPrice;
}
