package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.*;
import com.ismhac.jspace.model.enums.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_post")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    Company company;
    String title;
    @Column(name = "job_type")
    @Convert(converter = JobTypeConverter.class)
    JobType jobType;
    @Convert(converter = LocationConverter.class)
    Location location;
    @Convert(converter = RankConverter.class)
    Rank rank;
    @Column(columnDefinition = "text")
    String description;
    @Column(name = "min_pay")
    int minPay;
    @Column(name = "max_pay")
    int maxPay;
    @Convert(converter = ExperienceConverter.class)
    Experience experience;
    int quantity;
    @Convert(converter = GenderConverter.class)
    Gender gender;
    @Column(name = "open_date")
    LocalDate openDate;
    @Column(name = "close_date")
    LocalDate closeDate;
    @Column(name = "post_status")
    @Convert(converter = PostStatusConverter.class)
    @Builder.Default
    PostStatus postStatus = PostStatus.OPEN;
    @Builder.Default
    Boolean deleted = false;
}
