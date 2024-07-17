package com.ismhac.jspace.dto.candidateProfile.request;

import com.ismhac.jspace.model.enums.Experience;
import com.ismhac.jspace.model.enums.Gender;
import com.ismhac.jspace.model.enums.Location;
import com.ismhac.jspace.model.enums.Rank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateSurveyRequest {
    Integer candidateId;

    Gender gender;

    Experience experience;

    Integer minSalary;

    Integer maxSalary;

    Rank rank;

    Location location;

    String detailAddress;

    List<Integer> skills;
}
