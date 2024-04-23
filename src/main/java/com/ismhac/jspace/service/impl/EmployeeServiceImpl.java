package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.user.employee.request.EmployeeUpdateRequest;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.CompanyMapper;
import com.ismhac.jspace.mapper.EmployeeMapper;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.repository.CompanyRepository;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.EmployeeService;
import com.ismhac.jspace.util.BeanUtils;
import com.ismhac.jspace.util.PageUtils;
import com.ismhac.jspace.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    private final PageUtils pageUtils;
    private final UserUtils userUtils;

    private final UserMapper userMapper;

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    private BeanUtils beanUtils;

    @Override
    public PageResponse<EmployeeDto> getPageByCompanyIdFilterByEmailAndName(int companyId, String email, String name, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.getPageByCompanyIdFilterByEmailAndName(companyId, email, name, pageable);
        return pageUtils.toPageResponse(employeeMapper.toEmployeeDtoPage(employeePage));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public UserDto update(int id, EmployeeUpdateRequest request) {

        User tokenUser = userUtils.getUserFromToken();

        if(tokenUser.getId() != id){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Employee employee = employeeRepository.findByUserId(id)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        User user = employee.getId().getUser();

        org.springframework.beans.BeanUtils.copyProperties(request, user,beanUtils.getNullPropertyNames(request));

        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('EMPLOYEE')")
    public PageResponse<CompanyDto> getPageCompany(String name, String address, Pageable pageable) {
        return pageUtils.toPageResponse(CompanyMapper.instance.ePageToDtoPage(companyRepository.getPage(name, address, pageable)));
    }
}
