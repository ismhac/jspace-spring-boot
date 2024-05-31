package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.candidatePost.request.CandidatePostCreateRequest;
import com.ismhac.jspace.dto.candidatePost.response.CandidatePostDto;
import com.ismhac.jspace.dto.candidatePostLiked.response.CandidatePostLikedDto;
import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.post.PostDto;
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

    @DeleteMapping("/{id}/delete-resume")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Map<String, Object>> deleteResume(
            @PathVariable("id") int id,
            @RequestParam("resumeId") int resumeId){
        var result = candidateService.deleteResume(id, resumeId);
        return ApiResponse.<Map<String, Object>>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{id}/posts/like")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<CandidatePostLikedDto> likePost(
            @PathVariable("id") int id,
            @RequestParam("postId") int postId){
        var result = candidateService.likePost(id, postId);
        return ApiResponse.<CandidatePostLikedDto>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}/posts/unlike")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Boolean> unlikePost(
            @PathVariable("id") int id,
            @RequestParam("postId") int postId){
        var result = candidateService.unlikePost(id, postId);
        return ApiResponse.<Boolean>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{id}/posts/like")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<PageResponse<PostDto>> getPagePostLiked(
            @PathVariable("id") int id,
            Pageable pageable){
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);
        var result = candidateService.getPagePostLiked(id, adjustedPageable);
        return ApiResponse.<PageResponse<PostDto>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{id}/posts")
    public ApiResponse<PageResponse<Map<String, Object>>> getPagePost(
            @PathVariable("id") int id,
            Pageable pageable){
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);
        var result = candidateService.getPagePost(id, adjustedPageable);
        return ApiResponse.<PageResponse<Map<String, Object>>>builder()
                .result(result)
                .build();
    }

    @PostMapping("/posts/apply")
    public ApiResponse<CandidatePostDto> applyPost(@RequestBody CandidatePostCreateRequest request){
        var result = candidateService.applyPost(request);
        return ApiResponse.<CandidatePostDto>builder()
                .result(result)
                .build();
    }

    @GetMapping("{id}/posts/{postId}")
    public ApiResponse<Map<String, Object>> getPostById(
            @PathVariable("id") int candidateId,
            @PathVariable("postId") int postId){
        var result = candidateService.getPostById(candidateId, postId);
        return ApiResponse.<Map<String, Object>>builder()
                .result(result)
                .build();
    }
}
