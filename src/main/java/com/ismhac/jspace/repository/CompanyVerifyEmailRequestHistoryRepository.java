package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.CompanyVerifyEmailRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyVerifyEmailRequestHistoryRepository
        extends JpaRepository<CompanyVerifyEmailRequestHistory, Integer> {

    @Query("""
            select cverh from CompanyVerifyEmailRequestHistory cverh where cverh.token = :token
            """)
    Optional<CompanyVerifyEmailRequestHistory> findByToken(String token);
}
