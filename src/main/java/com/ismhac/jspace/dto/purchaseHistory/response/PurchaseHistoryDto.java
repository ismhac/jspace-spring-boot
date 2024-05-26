package com.ismhac.jspace.dto.purchaseHistory.response;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.model.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseHistoryDto {

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

    String status;

    String paymentMethod;
    // enum paypal: FAIL, PAID.
}