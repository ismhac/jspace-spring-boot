package com.ismhac.jspace.dto.user.candidate.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateUpdateRequest {
    private String email;

    private String name;

    private String phone;
}
