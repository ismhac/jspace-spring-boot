package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.resume.request.ResumeCreateRequest;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import org.springframework.web.multipart.MultipartFile;

public interface CandidateService {
    ResumeDto createResume(ResumeCreateRequest resumeCreateRequest, MultipartFile file);
}
