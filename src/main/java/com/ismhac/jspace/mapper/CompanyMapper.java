package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyMapper instance = Mappers.getMapper(CompanyMapper.class);

    @Mapping(target = "id", source = "id", qualifiedByName = "getCompanyId")
    @Mapping(target = "followerNumber", expression = "java(countFollowerOfCompany(e.getId(), candidateFollowCompanyRepository))")
    CompanyDto eToDto(Company e, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository);

    default Page<CompanyDto> ePageToDtoPage(Page<Company> ePage, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository) {
        return ePage.map(item -> this.eToDto(item, candidateFollowCompanyRepository));
    }

    @Named("getCompanyId")
    default int getCompanyId(int id){
        return id;
    }

    default int countFollowerOfCompany(int companyId, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return candidateFollowCompanyRepository.countFollowerOfCompany(companyId);
    }
}
