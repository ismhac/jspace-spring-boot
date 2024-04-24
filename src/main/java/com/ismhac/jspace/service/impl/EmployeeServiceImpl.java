package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.request.SendMailRequest;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.user.employee.request.EmployeeUpdateRequest;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.event.RequestCompanyVerifyEmailEvent;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.CompanyMapper;
import com.ismhac.jspace.mapper.EmployeeMapper;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.repository.CompanyRepository;
import com.ismhac.jspace.repository.CompanyVerifyEmailRequestHistoryRepository;
import com.ismhac.jspace.repository.EmployeeRepository;
import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.EmployeeService;
import com.ismhac.jspace.util.BeanUtils;
import com.ismhac.jspace.util.PageUtils;
import com.ismhac.jspace.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

    private final ApplicationEventPublisher applicationEventPublisher;
    private final CompanyVerifyEmailRequestHistoryRepository companyVerifyEmailRequestHistoryRepository;


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

    @Override
    public CompanyDto createCompany(CompanyCreateRequest request) {

        String email = request.getEmail().trim();
        String phone = request.getPhone().trim();

        Optional<Company> company = companyRepository.findByEmailOrPhone(email, phone);

        if(company.isPresent()){
            throw new AppException(ErrorCode.COMPANY_EXISTED);
        }

        Company newCompany = Company.builder()
                .name(request.getName())
                .background(request.getBackground())
                .logo(request.getLogo())
                .address(request.getAddress())
                .email(email)
                .phone(phone)
                .description(request.getDescription())
                .companyLink(request.getCompanyLink())
                .build();

        Company savedCompany = companyRepository.save(newCompany);

        _sendMailRequestCompanyVerifyEmail(savedCompany);

        return CompanyMapper.instance.eToDto(savedCompany);
    }

    private void _sendMailRequestCompanyVerifyEmail(Company company){

        String token = String.valueOf(UUID.randomUUID());
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);

        String body = "http://localhost:8081/jspace-service/companies/verify-email?mac=".concat(token);

        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .email(company.getEmail())
                .body(body)
                .subject("verify email (company)")
                .build();

        RequestCompanyVerifyEmailEvent event = new RequestCompanyVerifyEmailEvent(this, sendMailRequest);

        applicationEventPublisher.publishEvent(event);

        CompanyVerifyEmailRequestHistory companyVerifyEmailRequestHistory = CompanyVerifyEmailRequestHistory.builder()
                .company(company)
                .token(token)
                .expiryTime(expiryTime)
                .build();

        companyVerifyEmailRequestHistoryRepository.save(companyVerifyEmailRequestHistory);
    }
}
