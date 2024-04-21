package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.role.response.RoleDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.model.Role;
import com.ismhac.jspace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "role", source = "role", qualifiedByName = "convertRoleToRoleDto")
    UserDto toUserDto(User user);

    default Page<UserDto> toUserDtoPage(Page<User> userPage){
        return userPage.map(this::toUserDto);
    }

    @Named("convertRoleToRoleDto")
    default RoleDto convertRoleToRoleDto(Role role){
        return RoleMapper.instance.toRoleDto(role);
    }
}
