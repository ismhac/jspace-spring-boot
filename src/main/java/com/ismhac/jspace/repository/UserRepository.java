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
}
