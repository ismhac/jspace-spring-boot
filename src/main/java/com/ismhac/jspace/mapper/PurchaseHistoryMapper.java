package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.purchaseHistory.response.PurchaseHistoryDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.PurchaseHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchaseHistoryMapper {
    PurchaseHistoryMapper instance = Mappers.getMapper(PurchaseHistoryMapper.class);

    @Mapping(target = "company", source = "company", qualifiedByName = "convertCompanyToDto")
    PurchaseHistoryDto eToDto(PurchaseHistory e);

    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company company){
        return CompanyMapper.instance.eToDto(company);
    }
}
