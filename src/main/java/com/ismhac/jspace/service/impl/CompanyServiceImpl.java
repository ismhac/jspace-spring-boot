package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.mapper.CompanyMapper;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.CompanyVerifyEmailRequestHistory;
import com.ismhac.jspace.repository.CompanyRepository;
import com.ismhac.jspace.repository.CompanyVerifyEmailRequestHistoryRepository;
import com.ismhac.jspace.service.CompanyService;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyVerifyEmailRequestHistoryRepository
            companyVerifyEmailRequestHistoryRepository;

    private final PageUtils pageUtils;

    @Override
    public String verifyEmail(String token) {

        Optional<CompanyVerifyEmailRequestHistory> companyVerifyEmailRequestHistory =
                companyVerifyEmailRequestHistoryRepository.findByToken(token);

        if(companyVerifyEmailRequestHistory.isEmpty()){
            return "invalid request";
        }
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(companyVerifyEmailRequestHistory.get().getExpiryTime())){
            return "request expired";
        }

        Company company = companyVerifyEmailRequestHistory.get().getCompany();

        company.setEmailVerified(true);

        companyRepository.save(company);
        return "verify email successfully!";
    }
}
