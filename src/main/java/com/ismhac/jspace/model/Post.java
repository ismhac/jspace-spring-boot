package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.GenderConverter;
import com.ismhac.jspace.model.converter.JobTypeConverter;
import com.ismhac.jspace.model.converter.LocationConverter;
import com.ismhac.jspace.model.converter.PostStatusConverter;
import com.ismhac.jspace.model.enums.Gender;
import com.ismhac.jspace.model.enums.JobType;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.PostStatus;
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

    @Column(name = "employee_email")
    String employeeEmail;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;

    String title; // ten cong viec

    @Column(name = "job_type")
    @Convert(converter = JobTypeConverter.class)
    JobType jobType; // loai cong viec

    @Convert(converter = LocationConverter.class)
    Location location; // using enum;

    String rank; // vi tri using emum

    @Column(columnDefinition = "longtext")
    String description; // mo ta

    @Column(name = "min_pay")
    int minPay;

    @Column(name = "max_pay")
    int maxPay;

    String experience; // using enum

    int quantity; //  so luong

    @Convert(converter = GenderConverter.class)
    Gender gender;

    String skills; // skills is arrays

    @Column(name = "open_date")
    LocalDate openDate;

    @Column(name = "close_date")
    LocalDate closeDate;

    @Column(name = "post_status")
    @Convert(converter = PostStatusConverter.class)
    PostStatus postStatus;
}
