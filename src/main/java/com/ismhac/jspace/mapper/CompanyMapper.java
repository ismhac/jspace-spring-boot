package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.CompanyDto;
import com.ismhac.jspace.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toCompanyDto(Company company);
}
