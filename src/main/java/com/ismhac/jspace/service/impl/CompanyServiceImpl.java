package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.mapper.CompanyMapper;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.repository.CompanyRepository;
import com.ismhac.jspace.service.CompanyService;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    private final PageUtils pageUtils;

    /* get all companies and filter by name, address */
    @Override
    public PageResponse<CompanyDto> getPage(String name, String address, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(Math.max(pageNumber - 1, 0), (pageSize > 0 ? pageSize : 10));

        Page<Company> companyPage = companyRepository.getPage(name, address, pageable);

        return pageUtils.toPageResponse(companyMapper.toCompanyDtoPage(companyPage));
    }

    @Override
    public PageResponse<CompanyDto> getPageHasPost(String name, String address, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(Math.max(pageNumber - 1, 0), (pageSize > 0 ? pageSize : 10));

        Page<Company> companyPage = companyRepository.getPageHasPost(name, address, pageable);

        return pageUtils.toPageResponse(companyMapper.toCompanyDtoPage(companyPage));
    }

    @Override
    public PageResponse<CompanyDto> getPageNoPost(String name, String address, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(Math.max(pageNumber - 1, 0), (pageSize > 0 ? pageSize : 10));

        Page<Company> companyPage = companyRepository.getPageNoPost(name, address, pageable);

        return pageUtils.toPageResponse(companyMapper.toCompanyDtoPage(companyPage));
    }
}
