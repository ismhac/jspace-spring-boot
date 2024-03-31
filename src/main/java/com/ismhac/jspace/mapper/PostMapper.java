package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toPostDto(Post post);
}
