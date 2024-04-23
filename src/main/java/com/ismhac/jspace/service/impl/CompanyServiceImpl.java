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
}
