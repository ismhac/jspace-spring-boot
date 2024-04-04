package com.ismhac.jspace.model.converter;

import com.ismhac.jspace.model.enums.JobType;
import com.ismhac.jspace.model.enums.PostStatus;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

public class PostStatusConverter implements AttributeConverter<PostStatus, String> {
    @Override
    public String convertToDatabaseColumn(PostStatus postStatus) {
        return postStatus == null ? null : postStatus.getStatus();
    }

    @Override
    public PostStatus convertToEntityAttribute(String status) {
        if(status == null){
            return null;
        }

        return Stream.of(PostStatus.values())
                .filter(c->c.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
