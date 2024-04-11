package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.file.FileDto;
import com.ismhac.jspace.dto.resume.ResumeDto;
import com.ismhac.jspace.model.File;
import com.ismhac.jspace.model.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeMapper INSTANCE = Mappers.getMapper(ResumeMapper.class);

    @Mappings({
            @Mapping(target = "candidateId", source = "candidate.id.user.id"),
            @Mapping(target = "file", source = "file", qualifiedByName = "convertFileToFileDto")
    })
    ResumeDto toResumeDto(Resume resume);

    @Named("convertFileToFileDto")
    default FileDto convertFileToFileDto(File file) {
        return FileMapper.INSTANCE.toFileDto(file);
    }
}
