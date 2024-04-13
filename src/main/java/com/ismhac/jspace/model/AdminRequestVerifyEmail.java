package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_admin_request_verify_email")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminRequestVerifyEmail extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    Admin admin;

    @Column(unique = true)
    String token;

    @Column(name = "otp_created_date_time")
    LocalDateTime otpCreatedDateTime;
}
