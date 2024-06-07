package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.AdminForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminForgotPasswordTokenRepository extends JpaRepository<AdminForgotPasswordToken, Integer> {

    @Query("""
            select t1 from AdminForgotPasswordToken t1 where t1.admin.id.user.id = :adminId order by t1.id desc limit 1
            """)
    Optional<AdminForgotPasswordToken> findLatestByAdminId(@Param("adminId") int adminId);
}
