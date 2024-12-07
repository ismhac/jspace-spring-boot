package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.notification.response.NotificationDto;
import com.ismhac.jspace.dto.userNotification.response.UserNotificationDto;
import com.ismhac.jspace.model.Notification;
import com.ismhac.jspace.model.UserNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserNotificationMapper {
    UserNotificationMapper instance = Mappers.getMapper(UserNotificationMapper.class);

    @Mapping(target = "userId", source = "id.user.id")
    @Mapping(target = "notification", source = "id.notification", qualifiedByName = "convertNotificationToDto")
    @Mapping(target = "notificationTime", source = "createdAt")
    UserNotificationDto eToDto(UserNotification e);

    default Page<UserNotificationDto> ePageToDtoPage(Page<UserNotification> ePage){
        return ePage.map(this::eToDto);
    }

    @Named("convertNotificationToDto")
    default NotificationDto convertNotificationToDto(Notification notification){
        return NotificationMapper.instance.eToDto(notification);
    }
}
