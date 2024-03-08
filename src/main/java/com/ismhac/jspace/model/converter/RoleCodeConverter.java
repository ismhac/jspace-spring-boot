package com.ismhac.jspace.model.converter;

import com.ismhac.jspace.model.enums.RoleCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleCodeConverter implements AttributeConverter<RoleCode, String> {

    @Override
    public String convertToDatabaseColumn(RoleCode roleCode) {
        if (roleCode == null) {
            return null;
        }
        return roleCode.getCode();
    }

    @Override
    public RoleCode convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RoleCode.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
