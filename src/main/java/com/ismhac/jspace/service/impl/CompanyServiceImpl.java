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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyDTO create(CompanyCreateRequest companyCreateRequest) {
        Optional<Company> company = companyRepository.findCompanyByName(companyCreateRequest.getName());
        if(company.isPresent()){
            throw new BadRequestException(Status.COMPANY_EXIST_NAME);
        }

        Company newCompany = Company.builder()
                .name(companyCreateRequest.getName().trim())
                .background(companyCreateRequest.getBackground().trim())
                .logo(companyCreateRequest.getLogo().trim())
                .address(companyCreateRequest.getAddress().trim())
                .build();

        Company savedCompany = companyRepository.save(newCompany);
        CompanyDTO companyDTO = CompanyMapper.INSTANCE.toDTO(savedCompany);
        return companyDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyDTO update(CompanyUpdateRequest companyUpdateRequest) {
        Optional<Company> company = companyRepository.findCompanyByName(companyUpdateRequest.getName());
        if(company.isEmpty()){
            company.get().setName(companyUpdateRequest.getName());
            company.get().setBackground(companyUpdateRequest.getBackground());
            company.get().setLogo(companyUpdateRequest.getLogo());
            company.get().setAddress(companyUpdateRequest.getAddress());

            Company updatedCompany= companyRepository.save(company.get());
            CompanyDTO companyDTO = CompanyMapper.INSTANCE.toDTO(updatedCompany);
            return companyDTO;
        }
        else {
            CompanyCreateRequest companyCreateRequest = CompanyCreateRequest.builder()
                    .name(companyUpdateRequest.getName())
                    .background(companyUpdateRequest.getBackground())
                    .logo(companyUpdateRequest.getLogo())
                    .address(companyUpdateRequest.getAddress())
                    .build();

            CompanyDTO companyDTO = create(companyCreateRequest);
            return companyDTO;
        }
    }
}
