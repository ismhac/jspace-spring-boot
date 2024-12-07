package com.ismhac.jspace.model.converter;

import com.ismhac.jspace.model.enums.AdminType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class AdminTypeConverter implements AttributeConverter<AdminType, String> {
    @Override
    public String convertToDatabaseColumn(AdminType adminType) {
        return adminType == null ? null : adminType.getCode();
    }

    @Override
    public AdminType convertToEntityAttribute(String code) {
        if(code == null){
            return null;
        }

        return Stream.of(AdminType.values())
                .filter(c->c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
