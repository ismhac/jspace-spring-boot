package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.enums.RoleCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByCode(RoleCode roleCode);

    Optional<Role> findRoleByCode(RoleCode code);

    @Query("""
            select role
            from Role role
            where role.code not in (:superAdminRoleCode, :adminRoleCode)
            """)
    List<Role> findNonAdminRoles(RoleCode superAdminRoleCode, RoleCode adminRoleCode);
}
