package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.other.GenderDto;
import com.ismhac.jspace.dto.other.JobTypeDto;
import com.ismhac.jspace.dto.other.LocationDto;
import com.ismhac.jspace.dto.other.PostStatusDto;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.PostSkill;
import com.ismhac.jspace.model.enums.Gender;
import com.ismhac.jspace.model.enums.JobType;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.PostStatus;
import com.ismhac.jspace.repository.PostSkillRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper instance = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "company", source = "company", qualifiedByName = "convertCompanyToDto")
    @Mapping(target = "jobType", source = "jobType", qualifiedByName = "convertJobTypeToDto")
    @Mapping(target = "location", source = "location", qualifiedByName = "convertLocationToDto")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "convertGenderToDto")
    @Mapping(target = "postStatus", source = "postStatus", qualifiedByName = "convertPostStatusToDto")
    @Mapping(target = "skills", expression = "java(getSkillDtoList(e.getId(), postSkillRepository))")
    PostDto eToDto(Post e, @Context PostSkillRepository postSkillRepository);


    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company e){
        return CompanyMapper.instance.eToDto(e);
    }

    @Named("convertLocationToDto")
    default LocationDto convertLocationToDto(Location location){
        return LocationDto.builder()
                .value(location.name())
                .areaCode(location.getAreaCode())
                .province(location.getProvince())
                .build();
    }

    @Named("convertJobTypeToDto")
    default JobTypeDto convertJobTypeToDto(JobType jobType){
        return JobTypeDto.builder()
                .value(jobType.name())
                .code(jobType.getCode())
                .build();
    }

    @Named("convertGenderToDto")
    default GenderDto convertGenderToDto(Gender gender){
        return GenderDto.builder()
                .value(gender.name())
                .code(gender.getCode())
                .build();
    }

    @Named("convertPostStatusToDto")
    default PostStatusDto convertPostStatusToDto(PostStatus postStatus){
        return PostStatusDto.builder()
                .value(postStatus.name())
                .code(postStatus.getStatus())
                .build();
    }

    default List<SkillDto> getSkillDtoList(int postId, @Context PostSkillRepository postSkillRepository){
        return postSkillRepository.findAllSkillsByPostId(postId);
    }
}
