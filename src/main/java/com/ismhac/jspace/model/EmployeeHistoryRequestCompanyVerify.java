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
@Table(name = "tbl_employee_history_request_company_verify")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeHistoryRequestCompanyVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    Employee employee;
    String token;
    @Column(name = "expiry_time")
    LocalDateTime expiryTime;
}
