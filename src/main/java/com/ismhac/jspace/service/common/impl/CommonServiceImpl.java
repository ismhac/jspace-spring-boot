package com.ismhac.jspace.service.common.impl;

import com.ismhac.jspace.dto.other.*;
import com.ismhac.jspace.model.enums.*;
import com.ismhac.jspace.service.common.CommonService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public List<LocationDto> getLLocation() {
        return Arrays.stream(Location.values())
                .map(location ->
                        LocationDto.builder()
                                .value(location.name())
                                .areaCode(location.getAreaCode())
                                .province(location.getProvince())
                                .build()
                )
                .toList();
    }

    @Override
    public List<JobTypeDto> getJobType() {
        return Arrays.stream(JobType.values())
                .map(jobType ->
                        JobTypeDto.builder()
                                .value(jobType.name())
                                .code(jobType.getCode())
                                .build()
                )
                .toList();
    }

    @Override
    public List<GenderDto> getGender() {
        return Arrays.stream(Gender.values())
                .map(gender ->
                        GenderDto.builder()
                                .value(gender.name())
                                .code(gender.getCode())
                                .build()
                ).toList();
    }

    @Override
    public List<ApplyStatusDto> getApplyStatus() {
        return Arrays.stream(ApplyStatus.values())
                .map(applyStatus ->
                        ApplyStatusDto.builder()
                                .value(applyStatus.name())
                                .code(applyStatus.getStatus())
                                .build()
                ).toList();
    }

    @Override
    public List<RankDto> getRank() {
        return Arrays.stream(Rank.values())
                .map(rank ->
                        RankDto.builder()
                                .value(rank.name())
                                .code(rank.getCode())
                                .build()
                ).toList();
    }

    @Override
    public List<ExperienceDto> getExperience() {
        return Arrays.stream(Experience.values())
                .map(experience ->
                        ExperienceDto.builder()
                                .value(experience.name())
                                .code(experience.getCode())
                                .build()

                ).toList();
    }


}
