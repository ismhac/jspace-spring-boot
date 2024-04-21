package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper instance = Mappers.getMapper(PostMapper.class);

    PostDto toPostDto(Post post);
}
