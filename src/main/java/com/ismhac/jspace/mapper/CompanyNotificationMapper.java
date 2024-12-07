package com.ismhac.jspace.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.companyNotification.response.CompanyNotificationDto;
import com.ismhac.jspace.model.CompanyNotification;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CompanyNotificationMapper {
    CompanyNotificationMapper INSTANCE = Mappers.getMapper(CompanyNotificationMapper.class);

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(target = "notificationTime", source = "createdAt")
    @Mapping(target = "custom", source = "custom", qualifiedByName = "convertCustomStringToMap")
    CompanyNotificationDto toDto(CompanyNotification companyNotification);

    default Page<CompanyNotificationDto> toDtoPage(Page<CompanyNotification> companyNotifications) {
        return companyNotifications.map(this::toDto);
    }

    @Named("convertCustomStringToMap")
    default Map<String, Object> convertCustomStringToMap(String custom){
        if(StringUtils.isBlank(custom)) return null;
        Gson gson = new Gson();
        return gson.fromJson(custom, new TypeToken<Map<String, Object>>(){}.getType());
    }
}
