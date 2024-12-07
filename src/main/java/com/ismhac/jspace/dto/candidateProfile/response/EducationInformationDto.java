package com.ismhac.jspace.dto.candidateProfile.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EducationInformationDto {
    String schoolName;
    String major;
    Integer startMonth;
    Integer startYear;
    Integer endMonth;
    Integer endYear;
    String description;
    Boolean graduated;
}
