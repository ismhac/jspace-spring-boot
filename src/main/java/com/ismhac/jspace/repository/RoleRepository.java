package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByCode(String code);

    Role getRoleByCode(String code);
}
