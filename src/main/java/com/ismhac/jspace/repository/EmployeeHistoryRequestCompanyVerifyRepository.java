package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.EmployeeHistoryRequestCompanyVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeHistoryRequestCompanyVerifyRepository extends JpaRepository<EmployeeHistoryRequestCompanyVerify, Integer> {
    Optional<EmployeeHistoryRequestCompanyVerify> findByToken(String token);
}
