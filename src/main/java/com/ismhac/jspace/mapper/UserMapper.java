package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto toUserDto(User user);
}
