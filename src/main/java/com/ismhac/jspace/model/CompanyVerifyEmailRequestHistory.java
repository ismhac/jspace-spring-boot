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
@Table(name = "tbl_company_verify_email_request_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyVerifyEmailRequestHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    Company company;
    String token;
    @Column(name = "expiry_time")
    LocalDateTime expiryTime;
}
