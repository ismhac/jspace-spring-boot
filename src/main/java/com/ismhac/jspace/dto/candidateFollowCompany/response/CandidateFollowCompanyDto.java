package com.ismhac.jspace.dto.candidateFollowCompany.response;

import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateFollowCompanyDto {
    UserDto candidate;
    CompanyDto company;
}
