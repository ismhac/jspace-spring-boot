package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.file.response.FileDto;
import com.ismhac.jspace.model.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileMapper instance  = Mappers.getMapper(FileMapper.class);

    FileDto toFileDto(File file);
}
