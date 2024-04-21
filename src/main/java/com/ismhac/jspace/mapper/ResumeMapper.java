package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.file.response.FileDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.model.File;
import com.ismhac.jspace.model.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeMapper instance = Mappers.getMapper(ResumeMapper.class);

    @Mappings({
            @Mapping(target = "candidateId", source = "candidate.id.user.id"),
            @Mapping(target = "file", source = "file", qualifiedByName = "convertFileToFileDto")
    })
    ResumeDto toResumeDto(Resume resume);

    @Named("convertFileToFileDto")
    default FileDto convertFileToFileDto(File file) {
        return FileMapper.instance.toFileDto(file);
    }

    default Page<ResumeDto> toResumeDtoPage(Page<Resume> resumePage){
        return resumePage.map(this::toResumeDto);
    }
}
