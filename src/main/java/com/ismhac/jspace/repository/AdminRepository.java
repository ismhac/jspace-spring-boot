package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Admin;
import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.enums.RoleCode;
import com.ismhac.jspace.model.primaryKey.AdminId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, AdminId> {
    @Query("""
            select admin
            from Admin admin
            join User user on admin.id.user.id = user.id
            join Role role on user.role.id = role.id
            where user.email = :email
                and admin.type = :adminType
                and role.code = :roleCode
            """)
    Optional<Admin> findAdminByEmailAndAdminTypeAndRoleCode(String email, AdminType adminType, RoleCode roleCode);
}
