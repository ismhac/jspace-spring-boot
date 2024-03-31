package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    /* get page company and filter by name, address */
    @Query("""
            select t1
            from Company t1
            where (:name is null or lower(t1.name) like lower(concat('%', :name, '%')))
                and (:address is null or lower(t1.address) like lower(concat('%', :address, '%')))
            """)
    Page<Company> getPage(@Param("name") String name, @Param("address") String address, Pageable pageable);

    @Query("""
            select t1
            from Company t1
            where (:name is null or lower(t1.name) like lower(concat('%', :name, '%')))
                and (:address is null or lower(t1.address) like lower(concat('%', :address, '%')))
                and exists(
                    select 1
                    from Post t2
                    where t2.company = t1
                )
            """)
    Page<Company> getPageHasPost(@Param("name") String name, @Param("address") String address, Pageable pageable);

    @Query("""
            select t1
            from Company t1
            where (:name is null or lower(t1.name) like lower(concat('%', :name, '%')))
                and (:address is null or lower(t1.address) like lower(concat('%', :address, '%')))
                and (select count (t2) from Post t2 where t2.company = t1) = 0
            """)
    Page<Company> getPageNoPost(@Param("name") String name, @Param("address") String address, Pageable pageable);
}
