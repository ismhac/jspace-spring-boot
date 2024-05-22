package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.other.*;

import java.util.List;

public interface CommonService {
    List<LocationDto> getLLocation();
    List<JobTypeDto> getJobType();
    List<GenderDto> getGender();
    List<ApplyStatusDto> getApplyStatus();
    List<RankDto> getRank();
    List<ExperienceDto> getExperience();
}
