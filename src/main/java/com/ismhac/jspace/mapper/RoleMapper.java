package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.role.response.RoleDto;
import com.ismhac.jspace.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toRoleDto(Role role);

    List<RoleDto> toRoleDtoList(List<Role> roleList);
}
