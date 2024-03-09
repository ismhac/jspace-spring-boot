package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.company.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.CompanyDTO;
import com.ismhac.jspace.dto.company.CompanyUpdateRequest;
import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.mapper.CompanyMapper;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.repository.CompanyRepository;
import com.ismhac.jspace.service.CompanyService;
import com.ismhac.jspace.util.response.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
}
