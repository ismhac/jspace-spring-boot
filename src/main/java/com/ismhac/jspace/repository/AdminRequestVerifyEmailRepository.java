package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.AdminRequestVerifyEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRequestVerifyEmailRepository extends
        JpaRepository<AdminRequestVerifyEmail, Integer> {

    @Query("""
            select t1
            from AdminRequestVerifyEmail t1
            where t1.token = :token
            """)
    Optional<AdminRequestVerifyEmail> findByToken(String token);
}
