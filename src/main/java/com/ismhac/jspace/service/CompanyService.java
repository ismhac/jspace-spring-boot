package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;

public interface CompanyService {
    PageResponse<CompanyDto> getPage(String name, String address , int pageNumber, int pageSize);

    PageResponse<CompanyDto> getPageHasPost(String name, String address ,int pageNumber, int pageSize);

    PageResponse<CompanyDto> getPageNoPost(String name, String address ,int pageNumber, int pageSize);
}
