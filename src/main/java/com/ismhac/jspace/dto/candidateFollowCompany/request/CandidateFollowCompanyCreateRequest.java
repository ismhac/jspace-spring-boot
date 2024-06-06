package com.ismhac.jspace.dto.candidateFollowCompany.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateFollowCompanyCreateRequest {
    int companyId;
    int candidateId;
}
