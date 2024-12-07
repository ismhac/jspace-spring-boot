package com.ismhac.jspace.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismhac.jspace.dto.candidateProfile.response.CandidateProfileDto;
import com.ismhac.jspace.dto.candidateProfile.response.EducationInformationDto;
import com.ismhac.jspace.dto.candidateProfile.response.ExperienceInformationDto;
import com.ismhac.jspace.dto.common.dictionary.Dictionary;
import com.ismhac.jspace.dto.other.ExperienceDto;
import com.ismhac.jspace.dto.other.GenderDto;
import com.ismhac.jspace.dto.other.LocationDto;
import com.ismhac.jspace.dto.other.RankDto;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.model.CandidateProfile;
import com.ismhac.jspace.model.Skill;
import com.ismhac.jspace.model.enums.Experience;
import com.ismhac.jspace.model.enums.Gender;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.Rank;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateProfileMapper {
    CandidateProfileMapper instance = Mappers.getMapper(CandidateProfileMapper.class);

    @Mapping(target = "candidateId", source = "id.candidate.id.user.id")
    @Mapping(target = "skills", source = "skills", qualifiedByName = "convertSkillJsonToListSkillDto")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "convertGenDerToDto")
    @Mapping(target = "experience", source = "experience", qualifiedByName = "convertExperienceToDto")
    @Mapping(target = "rank", source = "rank", qualifiedByName = "convertRankToDto")
    @Mapping(target = "location", source = "location", qualifiedByName = "convertLocationToDto")
    @Mapping(target = "educationInfo", source = "educationInfo", qualifiedByName = "convertEducationJsonToDto")
    @Mapping(target = "experienceInfo", source = "experienceInfo", qualifiedByName = "convertExperienceInfoToDto")
    CandidateProfileDto eToDto(CandidateProfile e);

    @Named("convertGenDerToDto")
    default GenderDto convertGenDerToDto(Gender gender) {
        if(gender == null) return null;
        return GenderDto.builder()
                .value(gender.name())
                .code(gender.getCode())
                .language(Dictionary.genderDictionary.get(gender.name()))
                .build();
    }

    @Named("convertExperienceToDto")
    default ExperienceDto convertExperienceToDto(Experience experience) {
        if(experience == null) return null;
        return ExperienceDto.builder()
                .value(experience.name())
                .code(experience.getCode())
                .language(Dictionary.experienceDictionary.get(experience.name()))
                .build();
    }

    @Named("convertRankToDto")
    default RankDto convertRankToDto(Rank rank) {
        if(rank == null) return null;
        return RankDto.builder()
                .value(rank.name())
                .code(rank.getCode())
                .language(Dictionary.rankDictionary.get(rank.name()))
                .build();
    }

    @Named("convertLocationToDto")
    default LocationDto convertLocationToDto(Location location) {
        if (location == null) return null;
        return LocationDto.builder()
                .value(location.name())
                .areaCode(location.getAreaCode())
                .province(location.getProvince())
                .build();
    }

    @Named("convertSkillJsonToListSkillDto")
    default List<SkillDto> convertSkillJsonToListSkillDto(String skillJson) {
        if(StringUtils.isBlank(skillJson)) return null;
        Gson gson = new Gson();
        List<Skill> skills = gson.fromJson(skillJson, new TypeToken<List<Skill>>() {
        }.getType());
        return SkillMapper.instance.eListToDtoList(skills);
    }

    @Named("convertEducationJsonToDto")
    default EducationInformationDto convertEducationJsonToDto(String educationInfoJson) {
        if (StringUtils.isBlank(educationInfoJson)) return null;
        Gson gson = new Gson();
        return gson.fromJson(educationInfoJson, new TypeToken<EducationInformationDto>() {
        }.getType());
    }

    @Named("convertExperienceInfoToDto")
    default ExperienceInformationDto convertExperienceInfoToDto(String experienceInfoJson) {
        if (StringUtils.isBlank(experienceInfoJson)) return null;
        Gson gson = new Gson();
        return gson.fromJson(experienceInfoJson, new TypeToken<ExperienceInformationDto>() {
        }.getType());
    }

}
