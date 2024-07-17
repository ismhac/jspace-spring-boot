package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.candidateFollowCompany.request.CandidateFollowCompanyCreateRequest;
import com.ismhac.jspace.dto.candidateFollowCompany.response.CandidateFollowCompanyDto;
import com.ismhac.jspace.dto.candidatePost.request.CandidatePostCreateRequest;
import com.ismhac.jspace.dto.candidatePost.response.CandidatePostDto;
import com.ismhac.jspace.dto.candidatePostLiked.response.CandidatePostLikedDto;
import com.ismhac.jspace.dto.candidateProfile.request.CandidateSurveyRequest;
import com.ismhac.jspace.dto.candidateProfile.response.CandidateProfileDto;
import com.ismhac.jspace.dto.candidateProfile.response.EducationInformationDto;
import com.ismhac.jspace.dto.candidateProfile.response.ExperienceInformationDto;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.dto.user.candidate.request.CandidateUpdateRequest;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CandidateService {
    ResumeDto createResume(int id, String name, MultipartFile file);

    UserDto update(int id, CandidateUpdateRequest request);

    PageResponse<ResumeDto> getListResume(int id, Pageable pageable);

    UserDto updateAvatar(int id, MultipartFile avatar);

    UserDto updateBackground(int id, MultipartFile avatar);

    Map<String, Object> deleteBackground(int id, String backgroundId);

    Map<String, Object> deleteAvatar(int id, String avatarId);

    Map<String, Object> deleteResume(int id, int resumeId);

    CandidatePostLikedDto likePost(int id, int postId);

    boolean unlikePost(int id, int postId);

    PageResponse<PostDto> getPagePostLiked(int id, Pageable pageable);

    PageResponse<Map<String, Object>> getPagePost(int id, Pageable pageable);

    CandidatePostDto applyPost(CandidatePostCreateRequest request);

    Map<String, Object> getPostById(int candidateId, int postId);

    PageResponse<HashMap<String, Object>> getAppliedPost(int candidateId, Pageable pageable);

    CandidateFollowCompanyDto followCompany(CandidateFollowCompanyCreateRequest request);

    Boolean unFollowCompany(int candidateId, int companyId);

    PageResponse<CompanyDto> getPageFollowedCompanies(int candidateId, Pageable pageable);

    CandidateDto setDefaultResume(int candidateId, int resumeId);

    CandidateDto updatePublicProfile(int candidateId, boolean publicProfile);

    Boolean candidatePickSkills(int candidateId, List<Integer> skillsId);

    List<SkillDto> getSkillOfCandidate(int candidateId);

    CandidateProfileDto createCandidateSurvey(CandidateSurveyRequest request);

    CandidateProfileDto editEducationInformation(int candidateId,EducationInformationDto request);

    CandidateProfileDto editExperienceInformation(int candidateId, ExperienceInformationDto request);

    CandidateProfileDto getProfileDetail(int candidateId);
}
