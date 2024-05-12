package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.other.LocationDto;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.enums.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper instance = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "company", source = "company", qualifiedByName = "convertCompanyToDto")
    @Mapping(target = "location", source = "location", qualifiedByName = "convertLocationToDto")
    PostDto eToDto(Post e);

    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company e){
        return CompanyMapper.instance.eToDto(e);
    }

    @Named("convertLocationToDto")
    default LocationDto convertLocationToDto(Location location){
        return LocationDto.builder()
                .value(location.name())
                .areaCode(location.getAreaCode())
                .province(location.getProvince())
                .build();
    }
}
