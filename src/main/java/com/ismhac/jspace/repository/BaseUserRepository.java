package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Integer> {
    @Query("""
            select t1
            from BaseUser t1
            join Role t2 on t1.role.id = t2.id
            where t1.email = :email
                and t2.code = :roleCode
            """)
    Optional<BaseUser> findByEmailAndRoleCode(String email, String roleCode);

    Optional<BaseUser> findBaseUserByEmail(String email);
}
