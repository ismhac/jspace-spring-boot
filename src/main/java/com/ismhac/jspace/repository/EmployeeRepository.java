package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {

    /* Get page employee by company id and filter by email, name */
    @Query("""
            select t1
            from Employee t1
            where t1.company.id = :company_id
                and (:email is null or lower(t1.id.user.email) like lower(concat('%', cast(:email as text), '%')))
                and (:name is null or lower(t1.id.user.name) like lower(concat('%', cast(:name as text), '%')))
            """)
    Page<Employee> getPageByCompanyId(@Param("company_id") Integer companyId, @Param("email") String email, @Param("name") String name,Pageable pageable);
}
