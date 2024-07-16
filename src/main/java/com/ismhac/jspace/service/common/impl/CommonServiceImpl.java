package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.dto.common.dictionary.Dictionary;
import com.ismhac.jspace.dto.other.*;
import com.ismhac.jspace.model.enums.*;
import com.ismhac.jspace.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final Map<String, Map<String, String>> experienceDictionary = Dictionary.experienceDictionary;
    private final Map<String, Map<String, String>> jobTypeDictionary = Dictionary.jobTypeDictionary;
    private final Map<String, Map<String, String>> genderDictionary = Dictionary.genderDictionary;
    private final Map<String, Map<String, String>> applyStatusDictionary = Dictionary.applyStatusDictionary;
    private final Map<String, Map<String, String>> rankDictionary = Dictionary.rankDictionary;
    @Override
    public List<LocationDto> getLLocation() {
        return Arrays.stream(Location.values()).map(location -> LocationDto.builder().value(location.name()).areaCode(location.getAreaCode()).province(location.getProvince()).build()).toList();
    }

    @Override
    public List<JobTypeDto> getJobType() {
        return Arrays.stream(JobType.values()).map(jobType -> JobTypeDto.builder()
                .value(jobType.name())
                .code(jobType.getCode())
                .language(jobTypeDictionary.get(jobType.name()))
                .build()).toList();
    }

    @Override
    public List<GenderDto> getGender() {
        return Arrays.stream(Gender.values()).map(gender -> GenderDto.builder()
                .value(gender.name())
                .code(gender.getCode())
                .language(genderDictionary.get(gender.name()))
                .build()).toList();
    }

    @Override
    public List<ApplyStatusDto> getApplyStatus() {
        return Arrays.stream(ApplyStatus.values()).map(applyStatus -> ApplyStatusDto.builder()
                .value(applyStatus.name())
                .code(applyStatus.getStatus())
                .language(applyStatusDictionary.get(applyStatus.name()))
                .build()).toList();
    }

    @Override
    public List<RankDto> getRank() {
        return Arrays.stream(Rank.values()).map(rank -> RankDto.builder()
                .value(rank.name())
                .code(rank.getCode())
                .language(rankDictionary.get(rank.name()))
                .build()).toList();
    }

    @Override
    public List<ExperienceDto> getExperience() {
        return Arrays.stream(Experience.values()).map(experience -> ExperienceDto.builder()
                .value(experience.name())
                .code(experience.getCode())
                .language(experienceDictionary.get(experience.name()))
                .build()).toList();
    }
}
