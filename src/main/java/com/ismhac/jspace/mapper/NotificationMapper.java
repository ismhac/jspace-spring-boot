package com.ismhac.jspace.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.notification.response.NotificationDto;
import com.ismhac.jspace.model.Notification;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper instance = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "custom", source = "custom", qualifiedByName = "convertCustomStringToMap")
    NotificationDto eToDto(Notification e);

    @Named("convertCustomStringToMap")
    default Map<String, Object> convertCustomStringToMap(String custom){
        Gson gson = new Gson();
        if(StringUtils.isBlank(custom)) return null;
        return gson.fromJson(custom, new TypeToken<Map<String, Object>>(){}.getType());
    }
}
