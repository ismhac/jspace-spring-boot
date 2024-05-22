package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    int price;

    @Column(name = "number_of_post")
    int numberOfPost; // số lượng bài post mà gói sản phẩm này định nghĩa

    @Column(name = "post_duration")
    int postDuration;   // thời gian tồn tại của mỗi bài post

    @Column(name = "duration_day_number")
    int durationDayNumber;  // định nghĩa số ngày đến lúc hết hạn kể từ lúc mua gói sản phẩm này

    String description;
}
