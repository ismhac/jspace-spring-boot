package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.resume.ResumeCreateRequest;
import com.ismhac.jspace.dto.resume.ResumeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CandidateService {
    ResumeDto createResume(ResumeCreateRequest resumeCreateRequest, MultipartFile file);
}
