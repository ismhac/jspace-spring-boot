package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);

//    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);

    @Query("""
            select t1
            from User t1
            where (:role is null or t1.role.id = :role_id)
                and (:email is null or lower(t1.email) like lower(concat('%', :email, '%')))
                and (:name is null or lower(t1.name) like lower(concat('%', :name, '%')))
            """)
    Page<User> getPage(@Param("role_id") Integer  roleId, @Param("email") String email, @Param("name") String name, Pageable pageable);
}
