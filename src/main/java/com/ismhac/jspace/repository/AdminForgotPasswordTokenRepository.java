package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.AdminForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminForgotPasswordTokenRepository extends JpaRepository<AdminForgotPasswordToken, Integer> {

    @Query("""
            select afpt from AdminForgotPasswordToken afpt where afpt.admin.id.user.id = :adminId order by afpt.id desc limit 1
            """)
    Optional<AdminForgotPasswordToken> findLatestByAdminId(@Param("adminId") int adminId);
}
