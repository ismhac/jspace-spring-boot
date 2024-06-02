package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_purchase_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_price")
    double productPrice;

    @Column(name = "product_number_of_post")
    int productNumberOfPost;

    @Column(name = "product_post_duration")
    int productPostDuration;

    @Column(name = "product_duration_day_number")
    int productDurationDayNumber;

    LocalDate expiryDate;

    String description;

    int quantity;

    @Column(name = "total_price")
    double totalPrice;

    String status;

    @Column(name = "payment_method")
    String paymentMethod;
}
