package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("""
            select company
            from Company company
            where company.activateStatus = true
                and (:name is null or :name = '' or lower(company.name) like lower(concat('%', :name, '%')))
                and (:address is null or :address = '' or lower(company.address) like lower(concat('%', :address, '%')))
            """)
    Page<Company> getPage(@Param("name") String name, @Param("address") String address, Pageable pageable);

    @Query("""
            select company
            from Company company
            where company.email = :email or company.phone = :phone
            """)
    Optional<Company> findByEmailOrPhone(String email, String phone);

    @Query("""
            select company
            from Company company
            where (:name is null or :name = '' or lower(company.name) like lower(concat('%', :name, '%') ) )
                and (:address is null or :address = '' or lower(company.address) like lower(concat('%', :address, '%') ) )
                and (:email is null or :email = '' or lower(company.email) like lower(concat('%', :email, '%') ) )
                and (:phone is null or :phone = '' or lower(company.phone) like lower(concat('%', :phone, '%') ) )
                and (:emailVerified is null or company.emailVerified = :emailVerified)
                and (:verifiedByAdmin is null or company.verifiedByAdmin = :verifiedByAdmin)
            """)
    Page<Company> getPageAndFilter(String name, String address, String email, String phone, Boolean emailVerified, Boolean verifiedByAdmin, Pageable pageable);
}
