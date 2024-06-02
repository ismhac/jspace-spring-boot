package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompanyService {

    void verifyEmail(String token, HttpServletResponse httpServletResponse) throws IOException;

    void verifyEmployee(String token, HttpServletResponse httpServletResponse) throws IOException;

    CompanyDto getCompanyById(int id);

    CompanyDto updateLogo(int id, MultipartFile logo);

    CompanyDto updateBackground(int id, MultipartFile background);

    PageResponse<CompanyDto> getPageAndFilter(String name, String address, String email, String phone, String companySize, Pageable pageable);
}
