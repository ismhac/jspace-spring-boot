package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.company.CompanyDto;
import org.springframework.data.domain.Page;

public interface CompanyService {
    Page<CompanyDto> getPage(String name, String address ,int pageNumber, int pageSize);

    Page<CompanyDto> getPageHasPost(String name, String address ,int pageNumber, int pageSize);

    Page<CompanyDto> getPageNoPost(String name, String address ,int pageNumber, int pageSize);
}
