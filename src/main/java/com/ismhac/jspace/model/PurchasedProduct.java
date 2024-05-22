package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_purchased_product")
public class PurchasedProduct extends BaseEntity{ // sản phẩm công ty đã mua
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
    int productNumberOfPost;    // số lượng bài post còn lại

    @Column(name = "product_post_duration")
    int productPostDuration;    // thời gian của mỗi bài post (date number)

    @Column(name = "product_duration_day_number")
    int productDurationDayNumber; // số ngày kể từ ngày mua đến lúc hết hạn

    @Column(name = "expiry_date")
    LocalDate expiryDate;   // ngày hết hạn của gói sản phẩm này

    String description;

    int quantity;

    @Column(name = "total_price")
    int totalPrice;
}
