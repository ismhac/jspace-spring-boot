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
            where t1.username = :username or t1.email = :email
            """)
    Optional<User> findUserByUsernameOrEmail(String username, String email);

    @Query("""
            select t1
            from User t1
            where (:roleId is null or t1.role.id = :roleId)
                and (t1.role.id != 1)
                and (:name is null or :name = '' or  lower(t1.name) like lower(concat('%', :name, '%')))
                and (:email is null or :email = '' or lower(t1.email) like lower(concat('%', :email, '%')))
                and (:activated is null or t1.activated = :activated)
            """)
    Page<User> supperAdminGetPageUserAndFilterByNameAndEmailAndActivated(@Param("roleId") Integer roleId,@Param("name") String name, @Param("email") String email, @Param("activated") Boolean activated, Pageable pageable);

    @Query("""
            select t1
            from User t1
            where (:roleId is null or t1.role.id = :roleId)
                and (t1.role.id != 1 and t1.role.id != 2)
                and (:name is null or :name = '' or  lower(t1.name) like lower(concat('%', :name, '%')))
                and (:email is null or :email = '' or lower(t1.email) like lower(concat('%', :email, '%')))
                and (:activated is null or t1.activated = :activated)
            """)
    Page<User> adminGetPageUserAndFilterByNameAndEmailAndActivated(@Param("roleId") Integer roleId,@Param("name") String name, @Param("email") String email, @Param("activated") Boolean activated, Pageable pageable);
}
