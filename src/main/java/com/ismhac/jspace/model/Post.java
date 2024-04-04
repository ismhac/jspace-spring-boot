package com.ismhac.jspace.model;

import com.ismhac.jspace.model.converter.PostStatusConverter;
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

    @Column(name = "open_date")
    LocalDate openDate;

    @Column(name = "close_date")
    LocalDate closeDate;

    @Column(name = "post_status")
    @Convert(converter = PostStatusConverter.class)
    PostStatus postStatus;
}
