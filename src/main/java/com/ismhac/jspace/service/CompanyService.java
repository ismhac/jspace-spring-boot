package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.company.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.CompanyDTO;
import com.ismhac.jspace.dto.company.CompanyUpdateRequest;

public interface CompanyService {
    CompanyDTO create(CompanyCreateRequest companyCreateRequest);

    CompanyDTO update(CompanyUpdateRequest companyUpdateRequest);
}
