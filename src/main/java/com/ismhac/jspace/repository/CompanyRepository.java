package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findCompanyByName(String name);

    boolean existsById(int id);
}
