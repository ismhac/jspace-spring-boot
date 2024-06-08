package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.purchaseHistory.response.PurchaseHistoryDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.PurchaseHistory;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseHistoryMapper {
    PurchaseHistoryMapper instance = Mappers.getMapper(PurchaseHistoryMapper.class);

    @Mapping(target = "company", source = "company", qualifiedByName = "convertCompanyToDto")
    @Mapping(target = "purchasedDate", source = "createdAt")
    PurchaseHistoryDto eToDto(PurchaseHistory e, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository);

    default List<PurchaseHistoryDto> eListToDtoList(List<PurchaseHistory> eList, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return eList.stream().map(item-> this.eToDto(item, candidateFollowCompanyRepository)).toList();
    }

    default Page<PurchaseHistoryDto> ePageToDtoPage(Page<PurchaseHistory> ePage, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return ePage.map(item -> this.eToDto(item, candidateFollowCompanyRepository));
    }

    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company company, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return CompanyMapper.instance.eToDto(company, candidateFollowCompanyRepository);
    }
}
