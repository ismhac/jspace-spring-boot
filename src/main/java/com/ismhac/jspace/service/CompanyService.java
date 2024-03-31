package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.company.CompanyDto;
import org.springframework.data.domain.Page;

public interface CompanyService {
    PageResponse<CompanyDto> getPage(String name, String address , int pageNumber, int pageSize);

    PageResponse<CompanyDto> getPageHasPost(String name, String address ,int pageNumber, int pageSize);

    PageResponse<CompanyDto> getPageNoPost(String name, String address ,int pageNumber, int pageSize);
}
