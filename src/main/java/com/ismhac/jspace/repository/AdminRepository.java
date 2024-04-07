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
            where t1.type = :adminType
                and t1.id.user.username = :username
            """)
    Optional<Admin> findAdminByAdminTypeAndUsername(@Param("adminType") AdminType adminType,@Param("username") String username);

    @Query("""
            select t1
            from Admin t1
            where t1.type = :adminType
                and t1.id.user.username = :username
                and t1.id.user.email = :email
            """)
    Optional<Admin> findAdminByAdminTypeAndUsernameAndEmail(@Param("adminType") AdminType adminType,@Param("username") String username, @Param("email") String email);

    @Query("""
            select t1
            from Admin t1
            where t1.type = :adminType
                and t1.id.user.email = :email
            """)
    Optional<Admin> findAdminByAdminTypeAndEmail(@Param("adminType") AdminType adminType,@Param("email") String email);

    @Query("""
            select t1
            from Admin t1
            where t1.type = :type
                and (:name is null or :name ='' or lower(t1.id.user.name) like lower(concat('&', :name, '%') ) )
                and (:activated is null or t1.id.user.activated = :activated)
            """)
    Page<Admin> getPageAdminByTypeFilterByNameAndActivated(@Param("type") AdminType adminType, @Param("name") String name, @Param("activated") Boolean activated,Pageable pageable);
}
