package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.resume.ResumeCreateRequest;
import com.ismhac.jspace.dto.resume.ResumeDto;
import com.ismhac.jspace.service.CandidateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping("/create-resume")
    public ApiResponse<ResumeDto> createResume(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("name") ResumeCreateRequest resumeCreateRequest) {

        var result = candidateService.createResume(resumeCreateRequest, file);
        return ApiResponse.<ResumeDto>builder()
                .result(result)
                .build();
    }
}
