package com.ismhac.jspace.dto.purchasedProduct.response;

import com.ismhac.jspace.dto.common.response.BaseEntityDto;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchasedProductDto extends BaseEntityDto {
    int id;
    CompanyDto company;
    String productName;
    double productPrice;
    int productNumberOfPost;
    int productPostDuration;
    int productDurationDayNumber;
    LocalDate expiryDate;
    String description;
    int quantity;
    double totalPrice;
}
