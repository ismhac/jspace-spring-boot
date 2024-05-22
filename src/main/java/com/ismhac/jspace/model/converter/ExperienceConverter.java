package com.ismhac.jspace.model.converter;

import com.ismhac.jspace.model.enums.Experience;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ExperienceConverter implements AttributeConverter<Experience, String> {

    @Override
    public String convertToDatabaseColumn(Experience attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public Experience convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return null;
        }

        return Stream.of(Experience.values())
                .filter(c->c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
