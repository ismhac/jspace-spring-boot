package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.companyRequestReview.response.CompanyRequestReviewDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.CompanyRequestReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyRequestReviewMapper {
    CompanyRequestReviewMapper instance = Mappers.getMapper(CompanyRequestReviewMapper.class);

    @Mapping(target = "company", source = "id.company", qualifiedByName = "convertCompanyToCompanyDto")
    CompanyRequestReviewDto eToDto(CompanyRequestReview e);

    @Named("convertCompanyToCompanyDto")
    default CompanyDto convertCompanyToCompanyDto(Company company){
        return CompanyMapper.instance.eToDto(company);
    }

    default Page<CompanyRequestReviewDto> ePageToDtoPage(Page<CompanyRequestReview> ePage){
        return ePage.map(this::eToDto);
    }
}
