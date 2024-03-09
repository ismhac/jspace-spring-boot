package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.primaryKey.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {
}
