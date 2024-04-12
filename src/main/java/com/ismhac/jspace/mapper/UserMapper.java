package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "role", source = "role.code")
    UserDto toUserDto(User user);

    default Page<UserDto> toUserDtoPage(Page<User> userPage){
        return userPage.map(this::toUserDto);
    }
}
