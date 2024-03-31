package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.company.CompanyDto;
import com.ismhac.jspace.mapper.CompanyMapper;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.repository.CompanyRepository;
import com.ismhac.jspace.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    /* get all companies and filter by name, address */
    @Override
    public Page<CompanyDto> getPage(String name, String address, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Company> companyPage = companyRepository.getPage(name, address,pageable);

       List<CompanyDto> companyDtoList = companyPage.getContent().stream().map(companyMapper::toCompanyDto).toList();

        return new PageImpl<>(companyDtoList, pageable, companyPage.getTotalElements());
    }

    @Override
    public Page<CompanyDto> getPageHasPost(String name, String address, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Company> companyPage = companyRepository.getPageHasPost(name, address,pageable);

        List<CompanyDto> companyDtoList = companyPage.getContent().stream().map(companyMapper::toCompanyDto).toList();

        return new PageImpl<>(companyDtoList, pageable, companyPage.getTotalElements());
    }

    @Override
    public Page<CompanyDto> getPageNoPost(String name, String address, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Company> companyPage = companyRepository.getPageNoPost(name, address,pageable);

        List<CompanyDto> companyDtoList = companyPage.getContent().stream().map(companyMapper::toCompanyDto).toList();

        return new PageImpl<>(companyDtoList, pageable, companyPage.getTotalElements());
    }
}
