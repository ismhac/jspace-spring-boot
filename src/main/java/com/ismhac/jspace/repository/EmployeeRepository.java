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
    @Query("""
            select e from Employee e where e.company.id = :company_id
                and (:email is null or :email = '' or lower(e.id.user.email) like lower(concat('%', :email, '%')))
                and (:name is null or :name = '' or lower(e.id.user.name) like lower(concat('%', :name, '%')))
            """)
    Page<Employee> getPageByCompanyIdFilterByEmailAndName(@Param("company_id") Integer companyId, @Param("email") String email, @Param("name") String name,Pageable pageable);


    @Query("""
            select e from Employee e where e.id.user.id = :id
            """)
    Optional<Employee> findByUserId(int id);


    @Query("""
            select employee from Employee employee where employee.id.user.email = :email
            """)
    Optional<Employee> findByEmail(String email);

    @Query("""
            select new map(
                employee.id.user as user,
                tbl_company as company,
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
                tbl_company.emailVerified as companyEmailVerified,
                tbl_company.verifiedByAdmin as companyVerified
            )
            from Employee employee
            left join Company tbl_company on employee.company = tbl_company
            where employee.id.user.email = :email
            """)
    Map<String, Object> getInfoByEmail(String email);
}
