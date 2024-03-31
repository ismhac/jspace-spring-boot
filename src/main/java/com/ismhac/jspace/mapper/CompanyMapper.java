package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.CompanyDto;
import com.ismhac.jspace.model.Company;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toCompanyDto(Company company);

    default Page<CompanyDto> toCompanyDtoPage(Page<Company> companyPage){
        return companyPage.map(this::toCompanyDto);
    }
}
