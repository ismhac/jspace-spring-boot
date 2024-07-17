package com.ismhac.jspace.dto.candidateProfile.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExperienceInformationDto {
    String companyName;
    String position;
    Integer startMonth;
    Integer startYear;
    Integer endMonth;
    Integer endYear;
    Boolean workingYesNo;
}
