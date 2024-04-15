package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.resume.request.ResumeCreateRequest;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.user.candidate.request.CandidateUpdateRequest;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.service.CandidateService;
import com.ismhac.jspace.util.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("api/v1/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidate")
public class CandidateController {

    private final CandidateService candidateService;
    private final PageUtils pageUtils;

    @PostMapping(value = "/{id}/create-resume",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ResumeDto> createResume(
            @PathVariable("id") int id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {

        var result = candidateService.createResume(id,name, file);
        return ApiResponse.<ResumeDto>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserDto> update(
            @PathVariable("id") int id,
            @RequestBody CandidateUpdateRequest request){
        var result = candidateService.update(id, request);
        return ApiResponse.<UserDto>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{id}/resumes")
    public ApiResponse<PageResponse<ResumeDto>> getListResume(
            @PathVariable("id") int id, Pageable pageable){
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);
        var result = candidateService.getListResume(id, adjustedPageable);
        return ApiResponse.<PageResponse<ResumeDto>>builder()
                .result(result)
                .build();
    }
}
