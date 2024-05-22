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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/v1/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidate")
public class CandidateController {

    private final CandidateService candidateService;
    private final PageUtils pageUtils;

    @PostMapping(value = "/{id}/create-resume",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CANDIDATE')")
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
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<UserDto> update(
            @PathVariable("id") int id,
            @RequestBody CandidateUpdateRequest request){
        var result = candidateService.update(id, request);
        return ApiResponse.<UserDto>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{id}/resumes")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<PageResponse<ResumeDto>> getListResume(
            @PathVariable("id") int id, Pageable pageable){
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);
        var result = candidateService.getListResume(id, adjustedPageable);
        return ApiResponse.<PageResponse<ResumeDto>>builder()
                .result(result)
                .build();
    }

    @PutMapping(value = "/{id}/update-avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<UserDto> updateAvatar(
            @PathVariable("id") int id,
            @RequestParam("file") MultipartFile avatar){
        var result = candidateService.updateAvatar(id, avatar);
        return ApiResponse.<UserDto>builder()
                .result(result)
                .build();
    }

    @PutMapping(value = "/{id}/update-background",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<UserDto> updateBackground(
            @PathVariable("id") int id,
            @RequestParam("file") MultipartFile background){
        var result = candidateService.updateBackground(id, background);
        return ApiResponse.<UserDto>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}/delete-background")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Map<String, Object>> deleteBackground(
            @PathVariable("id") int id,
            @RequestParam("backgroundId") String backgroundId){
        var result = candidateService.deleteBackground(id, backgroundId);
        return ApiResponse.<Map<String, Object>>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}/delete-avatar")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Map<String, Object>> deleteAvatar(
            @PathVariable("id") int id,
            @RequestParam("avatarId") String avatarId){
        var result = candidateService.deleteAvatar(id, avatarId);
        return ApiResponse.<Map<String, Object>>builder()
                .result(result)
                .build();
    }
}
