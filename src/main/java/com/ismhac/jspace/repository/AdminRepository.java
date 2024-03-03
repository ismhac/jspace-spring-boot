package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Admin;
import com.ismhac.jspace.model.primaryKey.AdminID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, AdminID> {
    @Query("""
            select (count(t1) > 0)
            from Admin t1
            where t1.adminID.baseUser.id = :id
            """)
    boolean existsAdminByID(int id);
}
