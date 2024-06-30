package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.candidateFollowCompany.request.CandidateFollowCompanyCreateRequest;
import com.ismhac.jspace.dto.candidateFollowCompany.response.CandidateFollowCompanyDto;
import com.ismhac.jspace.dto.candidatePost.request.CandidatePostCreateRequest;
import com.ismhac.jspace.dto.candidatePost.response.CandidatePostDto;
import com.ismhac.jspace.dto.candidatePostLiked.response.CandidatePostLikedDto;
import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.user.candidate.request.CandidateUpdateRequest;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
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

import java.util.Map;

@RestController
@RequestMapping("api/v1/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidate")
public class CandidateController {

    private final CandidateService candidateService;
    private final PageUtils pageUtils;

    @PostMapping(value = "/{id}/create-resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<ResumeDto> createResume(@PathVariable("id") int id, @RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        return ApiResponse.<ResumeDto>builder().result(candidateService.createResume(id, name, file)).build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<UserDto> update(@PathVariable("id") int id, @RequestBody CandidateUpdateRequest request) {
        return ApiResponse.<UserDto>builder().result(candidateService.update(id, request)).build();
    }

    @GetMapping("/{id}/resumes")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<PageResponse<ResumeDto>> getListResume(@PathVariable("id") int id, Pageable pageable) {
        return ApiResponse.<PageResponse<ResumeDto>>builder().result(candidateService.getListResume(id, pageUtils.adjustPageable(pageable))).build();
    }

    @PutMapping(value = "/{id}/update-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<UserDto> updateAvatar(@PathVariable("id") int id, @RequestParam("file") MultipartFile avatar) {
        return ApiResponse.<UserDto>builder().result(candidateService.updateAvatar(id, avatar)).build();
    }

    @PutMapping(value = "/{id}/update-background", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<UserDto> updateBackground(@PathVariable("id") int id, @RequestParam("file") MultipartFile background) {
        return ApiResponse.<UserDto>builder().result(candidateService.updateBackground(id, background)).build();
    }

    @DeleteMapping("/{id}/delete-background")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Map<String, Object>> deleteBackground(@PathVariable("id") int id, @RequestParam("backgroundId") String backgroundId) {
        return ApiResponse.<Map<String, Object>>builder().result(candidateService.deleteBackground(id, backgroundId)).build();
    }

    @DeleteMapping("/{id}/delete-avatar")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Map<String, Object>> deleteAvatar(@PathVariable("id") int id, @RequestParam("avatarId") String avatarId) {
        return ApiResponse.<Map<String, Object>>builder().result(candidateService.deleteAvatar(id, avatarId)).build();
    }

    @DeleteMapping("/{id}/delete-resume")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Map<String, Object>> deleteResume(@PathVariable("id") int id, @RequestParam("resumeId") int resumeId) {
        return ApiResponse.<Map<String, Object>>builder().result(candidateService.deleteResume(id, resumeId)).build();
    }

    @PostMapping("/{id}/posts/like")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<CandidatePostLikedDto> likePost(@PathVariable("id") int id, @RequestParam("postId") int postId) {
        return ApiResponse.<CandidatePostLikedDto>builder().result(candidateService.likePost(id, postId)).build();
    }

    @DeleteMapping("/{id}/posts/unlike")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Boolean> unlikePost(@PathVariable("id") int id, @RequestParam("postId") int postId) {
        return ApiResponse.<Boolean>builder().result(candidateService.unlikePost(id, postId)).build();
    }

    @GetMapping("/{id}/posts/like")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<PageResponse<PostDto>> getPagePostLiked(@PathVariable("id") int id, Pageable pageable) {
        return ApiResponse.<PageResponse<PostDto>>builder().result(candidateService.getPagePostLiked(id, pageUtils.adjustPageable(pageable))).build();
    }

    @GetMapping("/{id}/posts")
    public ApiResponse<PageResponse<Map<String, Object>>> getPagePost(@PathVariable("id") int id, Pageable pageable) {
        return ApiResponse.<PageResponse<Map<String, Object>>>builder().result(candidateService.getPagePost(id, pageUtils.adjustPageable(pageable))).build();
    }

    @PostMapping("/posts/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<CandidatePostDto> applyPost(@RequestBody CandidatePostCreateRequest request) {
        return ApiResponse.<CandidatePostDto>builder().result(candidateService.applyPost(request)).build();
    }

    @GetMapping("/{id}/posts/{postId}")
    public ApiResponse<Map<String, Object>> getPostById(@PathVariable("id") int candidateId, @PathVariable("postId") int postId) {
        return ApiResponse.<Map<String, Object>>builder().result(candidateService.getPostById(candidateId, postId)).build();
    }

    @GetMapping("/{id}/posts/applied")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<PageResponse<?>> getAppliedPost(@PathVariable("id") int candidateId, Pageable pageable) {
        return ApiResponse.<PageResponse<?>>builder().result(candidateService.getAppliedPost(candidateId, pageUtils.adjustPageable(pageable))).build();
    }

    @PostMapping("/companies/follows")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<CandidateFollowCompanyDto> followCompany(@RequestBody CandidateFollowCompanyCreateRequest request) {
        return ApiResponse.<CandidateFollowCompanyDto>builder().result(candidateService.followCompany(request)).build();
    }

    @DeleteMapping("/{id}/companies/unfollow")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<Boolean> unFollowCompany(@PathVariable("id") int candidateId, @RequestParam("companyId") int companyId){
        return ApiResponse.<Boolean>builder().result(candidateService.unFollowCompany(candidateId, companyId)).build();
    }

    @GetMapping("/{id}/companies/follows")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<PageResponse<CompanyDto>> getPageFollowedCompanies(@PathVariable("id") int candidateId, Pageable pageable) {
        return ApiResponse.<PageResponse<CompanyDto>>builder().result(candidateService.getPageFollowedCompanies(candidateId, pageable)).build();
    }

    @PutMapping("/{id}/resumes/default-resume")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<CandidateDto> setDefaultResume(@PathVariable("id") int candidateId, @RequestParam("resumeId") int resumeId){
        return ApiResponse.<CandidateDto>builder().result(candidateService.setDefaultResume(candidateId, resumeId)).build();
    }

    @PutMapping("/{id}/profiles")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<CandidateDto> updatePublicProfile(@PathVariable("id") int candidateId, @RequestParam("publicProfile") boolean publicProfile){
        return ApiResponse.<CandidateDto>builder().result(candidateService.updatePublicProfile(candidateId, publicProfile)).build();
    }
}
