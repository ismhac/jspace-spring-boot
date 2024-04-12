package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    PageResponse<EmployeeDto> getPageByCompanyIdFilterByEmailAndName(int companyId, String email, String name, Pageable pageable);
 }
