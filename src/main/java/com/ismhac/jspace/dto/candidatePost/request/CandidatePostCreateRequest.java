package com.ismhac.jspace.dto.candidatePost.request;

import lombok.Data;

@Data
public class CandidatePostCreateRequest {
    int candidateId;
    int postId;
    int resumeId;
}
