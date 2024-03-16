package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);

    @Query("""
            select user
            from User user
            join OAuth2Info oAuth2Info on user.id = oAuth2Info.id.user.id
            where oAuth2Info.email = :email
            """)
    Optional<User> findUserByOAuth2Info(String email);
}
