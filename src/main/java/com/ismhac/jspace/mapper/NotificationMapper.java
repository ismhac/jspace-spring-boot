package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.notification.response.NotificationDto;
import com.ismhac.jspace.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper instance = Mappers.getMapper(NotificationMapper.class);

    NotificationDto eToDto(Notification e);
}
