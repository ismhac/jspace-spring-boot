package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.other.*;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.Post;
import com.ismhac.jspace.model.enums.*;
import com.ismhac.jspace.repository.PostSkillRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper instance = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "company", source = "company", qualifiedByName = "convertCompanyToDto")
    @Mapping(target = "jobType", source = "jobType", qualifiedByName = "convertJobTypeToDto")
    @Mapping(target = "location", source = "location", qualifiedByName = "convertLocationToDto")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "convertGenderToDto")
    @Mapping(target = "postStatus", source = "postStatus", qualifiedByName = "convertPostStatusToDto")
    @Mapping(target = "rank", source = "rank", qualifiedByName = "convertRankToDto")
    @Mapping(target = "experience", source = "experience", qualifiedByName = "convertExperienceToDto")
    @Mapping(target = "skills", expression = "java(getSkillDtoList(e.getId(), postSkillRepository))")
    PostDto eToDto(Post e, @Context PostSkillRepository postSkillRepository);

    default Page<PostDto> ePageToDtoPage(Page<Post> ePage, @Context PostSkillRepository postSkillRepository){
        return ePage.map(item-> eToDto(item, postSkillRepository));
    }


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

    @Named("convertRankToDto")
    default RankDto convertRankToDto(Rank rank){
        return RankDto.builder()
                .value(rank.name())
                .code(rank.getCode())
                .build();
    }

    @Named("convertExperienceToDto")
    default ExperienceDto convertExperienceToDto(Experience experience){
        return ExperienceDto.builder()
                .value(experience.name())
                .code(experience.getCode())
                .build();
    }

    default List<SkillDto> getSkillDtoList(int postId, @Context PostSkillRepository postSkillRepository){
        return postSkillRepository.findAllSkillsByPostId(postId);
    }
}
