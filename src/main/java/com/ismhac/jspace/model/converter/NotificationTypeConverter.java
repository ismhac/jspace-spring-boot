package com.ismhac.jspace.model.converter;

import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.NotificationType;
import com.ismhac.jspace.model.enums.PostStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class NotificationTypeConverter implements AttributeConverter<NotificationType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NotificationType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public NotificationType convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }

        return Stream.of(NotificationType.values())
                .filter(c->c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
