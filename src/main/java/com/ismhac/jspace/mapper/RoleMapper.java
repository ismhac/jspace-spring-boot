package com.ismhac.jspace.mapper;

import com.ismhac.jspace.config.mapper.MapstructConfig;
import com.ismhac.jspace.dto.role.RoleDto;
import com.ismhac.jspace.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toRoleDto(Role role);

    List<RoleDto> toRoleDtoList(List<Role> roleList);
}
