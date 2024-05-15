package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.post.PostCreateRequest;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.dto.user.employee.request.EmployeeUpdateRequest;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    PageResponse<EmployeeDto> getPageByCompanyIdFilterByEmailAndName(
            int companyId, String email, String name, Pageable pageable);

    UserDto update(int id, EmployeeUpdateRequest request);

    PageResponse<CompanyDto> getPageCompany(String name, String address, Pageable pageable);

    CompanyDto createCompany(CompanyCreateRequest request);

    String employeePickCompany(Integer companyId);

    EmployeeDto updateBackground(int id, MultipartFile background);

    PostDto createPost(PostCreateRequest req);

//    EmployeeDto updateAvatar() // todo
    EmployeeDto updateAvatar(int id, MultipartFile avatar);
 }
