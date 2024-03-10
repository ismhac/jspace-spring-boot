package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {
}
