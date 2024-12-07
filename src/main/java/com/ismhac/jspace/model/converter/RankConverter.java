package com.ismhac.jspace.model.converter;

import com.ismhac.jspace.model.enums.Rank;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RankConverter implements AttributeConverter<Rank, String> {
    @Override
    public String convertToDatabaseColumn(Rank attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public Rank convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(Rank.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
