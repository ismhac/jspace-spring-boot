package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.employee.EmployeeDto;
import com.ismhac.jspace.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    PageResponse<EmployeeDto> getPageByCompanyIdFilterByEmailAndName(int companyId, String email, String name, Pageable pageable);
 }
