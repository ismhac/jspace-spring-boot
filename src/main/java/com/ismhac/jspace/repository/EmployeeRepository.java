package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.primaryKey.EmployeeID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, EmployeeID> {
}
