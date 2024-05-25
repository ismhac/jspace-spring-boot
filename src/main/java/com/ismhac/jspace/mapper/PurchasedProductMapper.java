package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.purchasedProduct.response.PurchasedProductDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.PurchasedProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchasedProductMapper {
    PurchasedProductMapper instance = Mappers.getMapper(PurchasedProductMapper.class);

    @Mapping(target = "company", source = "company", qualifiedByName = "convertCompanyToDto")
    PurchasedProductDto eToDto(PurchasedProduct e );

    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company company){
        return CompanyMapper.instance.eToDto(company);
    }
}
