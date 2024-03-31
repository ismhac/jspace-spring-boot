package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.employee.EmployeeDto;
import com.ismhac.jspace.model.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    PageResponse<EmployeeDto> getPage(int companyId, String email, String name, int pageNumber, int pageSize);
 }
