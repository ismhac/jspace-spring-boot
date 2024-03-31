package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.CompanyDto;
import com.ismhac.jspace.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyMapper INSTANCE  = Mappers.getMapper(CompanyMapper.class);

    CompanyDto toCompanyDto(Company company);

    default Page<CompanyDto> toCompanyDtoPage(Page<Company> companyPage){
        return companyPage.map(this::toCompanyDto);
    }
}
