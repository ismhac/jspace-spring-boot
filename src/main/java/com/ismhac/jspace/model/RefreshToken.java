package com.ismhac.jspace.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "base_user_id", referencedColumnName = "id", nullable = false)
    private BaseUser baseUser;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;
}
