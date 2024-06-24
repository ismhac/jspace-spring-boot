package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.companyNotification.response.CompanyNotificationDto;
import com.ismhac.jspace.model.CompanyNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CompanyNotificationMapper {
    CompanyNotificationMapper INSTANCE = Mappers.getMapper(CompanyNotificationMapper.class);

    @Mapping(source = "company.id", target = "companyId")
    CompanyNotificationDto toDto(CompanyNotification companyNotification);

    default Page<CompanyNotificationDto> toDtoPage(Page<CompanyNotification> companyNotifications) {
        return companyNotifications.map(this::toDto);
    }
}
