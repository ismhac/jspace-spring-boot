package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.employee.EmployeeDto;
import com.ismhac.jspace.mapper.EmployeeMapper;
import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.service.EmployeeService;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    private final PageUtils pageUtils;

    @Override
    public PageResponse<EmployeeDto> getPage(int companyId, String email, String name, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.getPageByCompanyId(companyId, email, name, pageable);
        return pageUtils.toPageResponse(employeeMapper.toEmployeeDtoPage(employeePage));
    }
}
