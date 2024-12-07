package com.ismhac.jspace.model.converter;

import com.ismhac.jspace.model.enums.Location;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class LocationConverter implements AttributeConverter<Location, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Location attribute) {
        return attribute == null ? null : attribute.getAreaCode();
    }

    @Override
    public Location convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }

        return Stream.of(Location.values())
                .filter(c->c.getAreaCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
