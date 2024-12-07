package com.ismhac.jspace.dto.user.candidate.response;

import com.ismhac.jspace.dto.candidateProfile.response.CandidateProfileDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.model.CandidateProfile;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    UserDto user;
    ResumeDto defaultResume;
    Boolean publicProfile;
    Boolean surveyed;
    CandidateProfileDto profile;
}
