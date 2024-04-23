package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.model.Employee;
import com.ismhac.jspace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {


    @Mapping(target = "user", source = "id.user", qualifiedByName = "convertToUserDto")
    EmployeeDto toEmployeeDto(Employee employee);

    default Page<EmployeeDto> toEmployeeDtoPage(Page<Employee> employeePage) {
        return employeePage.map(this::toEmployeeDto);
    }

    @Named("convertToUserDto")
    default UserDto convertToUserDto(User user) {
        return UserMapper.instance.toUserDto(user);
    }
}
