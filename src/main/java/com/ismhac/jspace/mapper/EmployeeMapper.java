package com.ismhac.jspace.mapper;

import com.ismhac.jspace.config.mapper.MapstructConfig;
import com.ismhac.jspace.dto.employee.EmployeeDTO;
import com.ismhac.jspace.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapstructConfig.class)
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "id.user.id"),
            @Mapping(target = "email", source = "id.user.email"),
            @Mapping(target = "activated", source = "id.user.activated"),
            @Mapping(target = "roleCode", source = "id.user.role.code")
    })
    EmployeeDTO toDTO(Employee employee);
}
