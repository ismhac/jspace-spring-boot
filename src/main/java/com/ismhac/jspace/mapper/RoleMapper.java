package com.ismhac.jspace.mapper;

import com.ismhac.jspace.config.mapper.MapstructConfig;
import com.ismhac.jspace.dto.role.RoleDTO;
import com.ismhac.jspace.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = MapstructConfig.class)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTO(Role role);

    List<RoleDTO> toDTOList(List<Role> roleList);
}
