package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.dto.user.admin.response.AdminDto;
import com.ismhac.jspace.model.Admin;
import com.ismhac.jspace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    @Mappings({
            @Mapping(target = "user", source = "id.user", qualifiedByName = "convertToUserDto")
    })
    AdminDto toAdminDto(Admin admin);

    default Page<AdminDto> toAdminDtoPage(Page<Admin> adminPage){
        return adminPage.map(this::toAdminDto);
    }

    @Named("convertToUserDto")
    default UserDto convertToUserDto(User user) {
        return UserMapper.INSTANCE.toUserDto(user);
    }
}
