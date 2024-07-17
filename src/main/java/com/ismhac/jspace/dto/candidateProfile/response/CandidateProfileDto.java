package com.ismhac.jspace.dto.candidateProfile.response;

import com.ismhac.jspace.dto.other.ExperienceDto;
import com.ismhac.jspace.dto.other.GenderDto;
import com.ismhac.jspace.dto.other.LocationDto;
import com.ismhac.jspace.dto.other.RankDto;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateProfileDto {

    Integer candidateId;

    GenderDto gender;

    ExperienceDto experience;

    Integer minSalary;

    Integer maxSalary;

    RankDto rank;

    LocationDto location;

    String detailAddress;

    List<SkillDto> skills;

    EducationInformationDto educationInfo;

    ExperienceInformationDto experienceInfo;
}
