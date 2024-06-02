package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.candidatePostLiked.response.CandidatePostLikedDto;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.model.CandidatePostLiked;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.repository.PostSkillRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CandidatePostLikedMapper {

    CandidatePostLikedMapper instance = Mappers.getMapper(CandidatePostLikedMapper.class);

    @Mapping(target = "candidate", source = "id.candidate.id.user", qualifiedByName = "convertUserToDto")
    @Mapping(target = "post", source = "id.post", qualifiedByName = "convertPostToDto")
    CandidatePostLikedDto eToDto(CandidatePostLiked e, @Context PostSkillRepository postSkillRepository);

    @Named("convertUserToDto")
    default UserDto convertUserToDto(User user){
        return UserMapper.instance.toUserDto(user);
    }

    @Named("convertPostToDto")
    default PostDto convertPostToDto(Post post, @Context PostSkillRepository postSkillRepository){
        return PostMapper.instance.eToDto(post, postSkillRepository);
    }
}
