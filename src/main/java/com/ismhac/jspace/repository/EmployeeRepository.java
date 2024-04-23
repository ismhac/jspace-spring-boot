package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {

    /* Get page employee by company id and filter by email, name */
    @Query("""
            select t1
            from Employee t1
            where t1.company.id = :company_id
                and (:email is null or :email = '' or lower(t1.id.user.email) like lower(concat('%', :email, '%')))
                and (:name is null or :name = '' or lower(t1.id.user.name) like lower(concat('%', :name, '%')))
            """)
    Page<Employee> getPageByCompanyIdFilterByEmailAndName(@Param("company_id") Integer companyId, @Param("email") String email, @Param("name") String name,Pageable pageable);


    @Query("""
            select t1
            from Employee t1
            where t1.id.user.id = :id
            """)
    Optional<Employee> findByUserId(int id);


    @Query("""
            select new map(
                employee.id.user as user,
                employee.verifiedByCompany as verifiedByCompany,
                case
                    when employee.id.user.email is not null
                        and employee.id.user.name is not null
                        and employee.id.user.phone is not null
                    then true else false
                end as hasFullCredentialInfo,
                case
                    when employee.company is not null
                    then true else false
                end as hasCompany,
                case
                    when employee.company is null
                    then false
                    else(
                        select tbl_company.verifiedByAdmin
                        from Company tbl_company
                        where tbl_company.id = employee.company.id
                    )
                end as companyVerified
            )
            from Employee employee
            where employee.id.user.email = :email
            """)
    Map<String, Object> getInfoByEmail(String email);
}
