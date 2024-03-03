package com.ismhac.jspace.mapper;

import com.ismhac.jspace.config.mapper.MapstructConfig;
import com.ismhac.jspace.dto.company.CompanyDTO;
import com.ismhac.jspace.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class)
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDTO toDTO(Company company);
}
