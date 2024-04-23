package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyMapper instance = Mappers.getMapper(CompanyMapper.class);

    CompanyDto eToDto(Company company);

    Company createReqToEntity(CompanyCreateRequest companyCreateRequest);

    default Page<CompanyDto> ePageToDtoPage(Page<Company> companyPage) {
        return companyPage.map(this::eToDto);
    }
}
