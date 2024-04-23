package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    CandidateMapper instance = Mappers.getMapper(CandidateMapper.class);

    @Mapping(target = "user", source = "id.user", qualifiedByName = "convertUserToUserDto")
    CandidateDto eToDto(Candidate e);

    @Named("convertUserToUserDto")
    default UserDto convertUserToUserDto(User user){
        return UserMapper.instance.toUserDto(user);
    }
}
