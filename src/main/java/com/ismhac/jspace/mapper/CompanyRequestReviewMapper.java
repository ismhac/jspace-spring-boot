package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.companyRequestReview.response.CompanyRequestReviewDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.CompanyRequestReview;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyRequestReviewMapper {
    CompanyRequestReviewMapper instance = Mappers.getMapper(CompanyRequestReviewMapper.class);

    @Mapping(target = "company", source = "id.company", qualifiedByName = "convertCompanyToCompanyDto")
    CompanyRequestReviewDto eToDto(CompanyRequestReview e, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository);

    @Named("convertCompanyToCompanyDto")
    default CompanyDto convertCompanyToCompanyDto(Company company, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return CompanyMapper.instance.eToDto(company, candidateFollowCompanyRepository);
    }

    default Page<CompanyRequestReviewDto> ePageToDtoPage(Page<CompanyRequestReview> ePage, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return ePage.map(item -> this.eToDto(item, candidateFollowCompanyRepository));
    }
}
