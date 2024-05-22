package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_purchase_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseHistory extends BaseEntity {   // lịch sử mua sản phẩm

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_price")
    int productPrice;

    @Column(name = "product_number_of_post")
    int productNumberOfPost;

    @Column(name = "product_post_duration")
    int productPostDuration;

    @Column(name = "product_duration_day_number")
    int productDurationDayNumber;

    String description;

    int quantity;

    @Column(name = "total_price")
    int totalPrice;
}
