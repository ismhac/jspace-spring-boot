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
            @Mapping(target = "id", source ="id.baseUser.id"),
            @Mapping(target = "email", source = "id.baseUser.email"),
            @Mapping(target = "isActivated", source = "id.baseUser.activated"),
            @Mapping(target = "roleCode", source = "id.baseUser.role.code")
    })
    EmployeeDTO toDTO(Employee employee);
}
