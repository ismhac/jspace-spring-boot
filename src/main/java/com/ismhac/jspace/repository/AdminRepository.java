package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Admin;
import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.model.primaryKey.AdminId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, AdminId> {
    Optional<Admin> findAdminById(AdminId adminId);

    @Query("""
            select t1
            from Admin t1
            where t1.id.user.username = :username
            """)
    Optional<Admin> findAdminByUsername(@Param("username") String username);

    @Query("""
            select t1
            from Admin t1
            where t1.type = :type
                and (:name is null or lower(t1.id.user.username) like lower(concat('%', :name, '%')))  
            """)
    Page<Admin> getPageAdminByType(@Param("type") AdminType adminType, String name, Pageable pageable);
}
