package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.user.UserDto;
import com.ismhac.jspace.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
}
