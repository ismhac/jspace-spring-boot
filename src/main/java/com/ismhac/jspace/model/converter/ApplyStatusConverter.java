package com.ismhac.jspace.model.converter;

import com.ismhac.jspace.model.enums.AdminType;
import com.ismhac.jspace.model.enums.ApplyStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ApplyStatusConverter implements AttributeConverter<ApplyStatus, String> {
    @Override
    public String convertToDatabaseColumn(ApplyStatus applyStatus) {
        return applyStatus == null ? null : applyStatus.getStatus();
    }
    @Override
    public ApplyStatus convertToEntityAttribute(String status) {
        if( status == null){
            return null;
        }

        return Stream.of(ApplyStatus.values())
                .filter(c->c.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
