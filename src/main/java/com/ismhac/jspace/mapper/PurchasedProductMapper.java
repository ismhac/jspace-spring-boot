package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.purchasedProduct.response.PurchasedProductDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.PurchasedProduct;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchasedProductMapper {
    PurchasedProductMapper instance = Mappers.getMapper(PurchasedProductMapper.class);

    @Mapping(target = "company", expression = "java(convertCompanyToDto(e.getCompany(), candidateFollowCompanyRepository))")
    PurchasedProductDto eToDto(PurchasedProduct e , @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository);

    default List<PurchasedProductDto> eListToDtoList(List<PurchasedProduct> eList, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return eList.stream().map(item-> this.eToDto(item, candidateFollowCompanyRepository)).toList();
    }

    default Page<PurchasedProductDto> ePageToDtoPage(Page<PurchasedProduct> ePage, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return ePage.map(item -> this.eToDto(item, candidateFollowCompanyRepository));
    }

    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company company, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return CompanyMapper.instance.eToDto(company, candidateFollowCompanyRepository);
    }

}
