package com.ismhac.jspace.dto.candidatePost.response;

import com.ismhac.jspace.dto.other.ApplyStatusDto;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import lombok.Data;

@Data
public class CandidatePostDto {
    CandidateDto candidate;
    PostDto post;
    ResumeDto resume;
    ApplyStatusDto applyStatus;
}
