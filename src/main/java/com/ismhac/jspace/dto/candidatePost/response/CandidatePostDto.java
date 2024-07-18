package com.ismhac.jspace.dto.candidatePost.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ismhac.jspace.dto.other.ApplyStatusDto;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import lombok.Data;

import java.time.Instant;

@Data
public class CandidatePostDto {
    CandidateDto candidate;
    PostDto post;
    ResumeDto resume;
    ApplyStatusDto applyStatus;
    String note;
    @JsonProperty("updatedTime")
    Instant updatedAt;
}
