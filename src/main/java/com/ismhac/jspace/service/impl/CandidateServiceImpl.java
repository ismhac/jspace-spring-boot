package com.ismhac.jspace.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import com.ismhac.jspace.dto.other.ApplyStatusDto;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.skill.response.SkillDto;
import com.ismhac.jspace.dto.user.candidate.request.CandidateUpdateRequest;
import com.ismhac.jspace.dto.user.candidate.response.CandidateDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.*;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.ApplyStatus;
import com.ismhac.jspace.model.enums.NotificationTitle;
import com.ismhac.jspace.model.enums.PostStatus;
import com.ismhac.jspace.model.primaryKey.*;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.CandidateService;
import com.ismhac.jspace.util.BeanUtils;
import com.ismhac.jspace.util.PageUtils;
import com.ismhac.jspace.util.UserUtils;
import com.ismhac.jspace.util.adapter.InstantDeserializer;
import com.ismhac.jspace.util.adapter.LocalDateAdapter;
import com.ismhac.jspace.util.adapter.LocalDateTimeTypeAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateServiceImpl implements CandidateService {
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final UserUtils userUtils;
    private final CandidateRepository candidateRepository;
    private final UserMapper userMapper;
    private final ResumeMapper resumeMapper;
    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;
    private final PageUtils pageUtils;
    private final PostRepository postRepository;
    private final CandidatePostLikedRepository candidatePostLikedRepository;
    private final PostSkillRepository postSkillRepository;
    private final CandidatePostRepository candidatePostRepository;
    private final CandidateFollowCompanyRepository candidateFollowCompanyRepository;
    private final CompanyRepository companyRepository;
    private final CompanyNotificationRepository companyNotificationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    @Autowired
    private BeanUtils beanUtils;
    @Autowired
    private SkillRepository skillRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResumeDto createResume(int id, String name, MultipartFile file) {
        User tokenUser = userUtils.getUserFromToken();
        if (tokenUser.getId() != id) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        try {
            User user = userUtils.getUserFromToken();
            Candidate candidate = candidateRepository.findById(CandidateId.builder().user(user).build()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
            File uploadedFile = uploadFile(file);
            Resume resume = Resume.builder().name(name).candidate(candidate).file(uploadedFile).build();
            return resumeMapper.toResumeDto(resumeRepository.save(resume));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasRole('CANDIDATE')")
    public UserDto update(int id, CandidateUpdateRequest request) {
        User tokenUser = userUtils.getUserFromToken();
        if (tokenUser.getId() != id) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        Candidate candidate = candidateRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User user = candidate.getId().getUser();
        org.springframework.beans.BeanUtils.copyProperties(request, user, beanUtils.getNullPropertyNames(request));
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('CANDIDATE')")
    public PageResponse<ResumeDto> getListResume(int id, Pageable pageable) {
        User tokenUser = userUtils.getUserFromToken();
        if (tokenUser.getId() != id) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        Page<Resume> resumePage = resumeRepository.findAllByCandidateId(id, pageable);
        return pageUtils.toPageResponse(resumeMapper.toResumeDtoPage(resumePage));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto updateAvatar(int id, MultipartFile avatar) {
        Candidate candidate = candidateRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (avatar == null || avatar.isEmpty()) {
            throw new IllegalArgumentException("background must not be empty");
        }
        Map<String, Object> options = new HashMap<>();
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(avatar.getBytes(), options);
            cloudinary.uploader().upload(avatar.getBytes(), options);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
        String avatarPath = (String) uploadResult.get("secure_url");
        String avatarId = (String) uploadResult.get("public_id");
        candidate.getId().getUser().setPicture(avatarPath);
        candidate.getId().getUser().setPictureId(avatarId);
        return UserMapper.instance.toUserDto(candidateRepository.save(candidate).getId().getUser());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto updateBackground(int id, MultipartFile avatar) {
        Candidate candidate = candidateRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (avatar == null || avatar.isEmpty()) {
            throw new IllegalArgumentException("background must not be empty");
        }
        Map<String, Object> options = new HashMap<>();
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(avatar.getBytes(), options);
            cloudinary.uploader().upload(avatar.getBytes(), options);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
        String backgroundPath = (String) uploadResult.get("secure_url");
        String backgroundId = (String) uploadResult.get("public_id");
        candidate.getId().getUser().setBackground(backgroundPath);
        candidate.getId().getUser().setBackgroundId(backgroundId);
        return UserMapper.instance.toUserDto(candidateRepository.save(candidate).getId().getUser());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteBackground(int id, String backgroundId) {
        try {
            Candidate candidate = candidateRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            if (!backgroundId.equals(candidate.getId().getUser().getBackgroundId()) || backgroundId.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_FILE_ID);
            }
            Map deleteResult = cloudinary.uploader().destroy(backgroundId, ObjectUtils.emptyMap());
            if (deleteResult.get("result").toString().equals("ok")) {
                candidate.getId().getUser().setBackgroundId(null);
                candidate.getId().getUser().setBackground(null);
                return new HashMap<>() {{
                    put("status", deleteResult);
                    put("user", UserMapper.instance.toUserDto(candidateRepository.save(candidate).getId().getUser()));
                }};
            } else {
                return new HashMap<>() {{
                    put("status", deleteResult);
                    put("user", UserMapper.instance.toUserDto(candidateRepository.save(candidate).getId().getUser()));
                }};
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.DELETE_FILE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteAvatar(int id, String avatarId) {
        try {
            Candidate candidate = candidateRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            if (!avatarId.equals(candidate.getId().getUser().getPictureId()) || avatarId.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_FILE_ID);
            }
            Map deleteResult = cloudinary.uploader().destroy(avatarId, ObjectUtils.emptyMap());
            if (deleteResult.get("result").toString().equals("ok")) {
                candidate.getId().getUser().setPicture(null);
                candidate.getId().getUser().setPictureId(null);
                return new HashMap<>() {{
                    put("status", deleteResult);
                    put("user", UserMapper.instance.toUserDto(candidateRepository.save(candidate).getId().getUser()));
                }};
            } else {
                return new HashMap<>() {{
                    put("status", deleteResult);
                    put("user", UserMapper.instance.toUserDto(candidateRepository.save(candidate).getId().getUser()));
                }};
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.DELETE_FILE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteResume(int id, int resumeId) {
        try {
            Candidate candidate = candidateRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            if(candidate.getDefaultResume().getId() == resumeId){
                candidate.setDefaultResume(null);
            }
            Resume resume = resumeRepository.findById(resumeId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_RESUME));
            resume.setUseYesNo(false);
            resumeRepository.save(resume);
            return new HashMap<>() {{
                put("status", true);
            }};
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.DELETE_FILE_FAIL);
        }
    }

    @Override
    public CandidatePostLikedDto likePost(int id, int postId) {
        Candidate candidate = candidateRepository.findByUserId(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_POST));
        CandidatePostLikedId candidatePostLikedId = CandidatePostLikedId.builder().candidate(candidate).post(post).build();
        CandidatePostLiked candidatePostLiked = CandidatePostLiked.builder().id(candidatePostLikedId).build();
        return CandidatePostLikedMapper.instance.eToDto(candidatePostLikedRepository.save(candidatePostLiked), postSkillRepository, candidateFollowCompanyRepository);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlikePost(int id, int postId) {
        candidatePostLikedRepository.findByCandiDateIdAndPostId(id, postId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_POST_LIKED));
        int deleted = candidatePostLikedRepository.deleteByCandidateIdAndPostId(id, postId);
        return deleted == 1;
    }

    @Override
    public PageResponse<PostDto> getPagePostLiked(int id, Pageable pageable) {
        Page<Post> posts = candidatePostLikedRepository.getPagePostByCandidateId(id, pageable);
        return pageUtils.toPageResponse(PostMapper.instance.ePageToDtoPage(posts, postSkillRepository, candidateFollowCompanyRepository));
    }

    @Override
    public PageResponse<Map<String, Object>> getPagePost(int id, Pageable pageable) {
        Page<Map<String, Object>> postPage = postRepository.candidateGetPagePost(id, LocalDate.now(), PostStatus.OPEN,pageable);
        List<Map<String, Object>> dtoList = postPage.getContent().stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>(item);
                    map.put("post", PostMapper.instance.eToDto((Post) item.get("post"), postSkillRepository, candidateFollowCompanyRepository));
                    return map;
                }).toList();
        return PageResponse.<Map<String, Object>>builder().content(dtoList).pageNumber(postPage.getNumber()).pageSize(postPage.getSize()).totalElements(postPage.getTotalElements()).totalPages(postPage.getTotalPages()).numberOfElements(postPage.getNumberOfElements()).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CandidatePostDto applyPost(CandidatePostCreateRequest request) {
        Optional<CandidatePost> candidatePostOptional = candidatePostRepository.findByCandidateIdAndPostId(request.getCandidateId(), request.getPostId());
        if (candidatePostOptional.isPresent()) {
            throw new AppException(ErrorCode.HAS_APPLIED);
        }
        Candidate candidate = candidateRepository.findByUserId(request.getCandidateId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_POST));
        Resume resume = resumeRepository.findById(request.getResumeId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_RESUME));
        CandidatePost candidatePost = CandidatePost.builder().id(CandidatePostId.builder().candidate(candidate).post(post).build()).resume(resume).applyStatus(ApplyStatus.PROGRESS).build();
        CompanyNotification companyNotification = CompanyNotification.builder()
                .company(post.getCompany())
                .title(NotificationTitle.NOTIFICATION_COMPANY_NEW_CANDIDATE_APPLY.getTitle())
                .notification("Candidate " + candidate.getId().getUser().getName() + " has applied for your post " + post.getTitle())
                .custom(new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(CandidatePostMapper.instance.eToDto(candidatePostRepository.save(candidatePost), postSkillRepository, candidateFollowCompanyRepository)))
                .read(false).build();
        companyNotificationRepository.save(companyNotification);
        return CandidatePostMapper.instance.eToDto(candidatePostRepository.save(candidatePost), postSkillRepository, candidateFollowCompanyRepository);
    }

    @Override
    public Map<String, Object> getPostById(int candidateId, int postId) {
        Map<String, Object> result = postRepository.candidateFindPostById(candidateId, postId);
        Map<String, Object> map = new HashMap<>(result);
        map.put("post", PostMapper.instance.eToDto((Post) result.get("post"), postSkillRepository, candidateFollowCompanyRepository));
        return map;
    }

    @Override
    public PageResponse<HashMap<String, Object>> getAppliedPost(int candidateId, Pageable pageable) {
        candidateRepository.findByUserId(candidateId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
        Page<Map<String, Object>> results = candidatePostRepository.candidateGetPageAppliedPost(candidateId, pageable);

        List<HashMap<String, Object>> customResults = results.getContent().stream().map(result -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("post", PostMapper.instance.eToDto((Post) result.get("post"), postSkillRepository, candidateFollowCompanyRepository));
            map.put("applyStatus", ApplyStatusDto.builder()
                            .code(((ApplyStatus) result.get("applyStatus")).name())
                            .value(((ApplyStatus) result.get("applyStatus")).getStatus())
                    .build());
            map.put("appliedDate", result.get("appliedDate"));
            return map;
        }).toList();

        Page<HashMap<String, Object>> customPage = new PageImpl<>(customResults, pageable, results.getTotalElements());

        return pageUtils.toPageResponse(customPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CandidateFollowCompanyDto followCompany(CandidateFollowCompanyCreateRequest request) {
        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_COMPANY));
        Candidate candidate = candidateRepository.findByUserId(request.getCandidateId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
        CandidateFollowCompanyId id = CandidateFollowCompanyId.builder().company(company).candidate(candidate).build();
        Optional<CandidateFollowCompany> candidateFollowCompanyOptional = candidateFollowCompanyRepository.findById(id);
        if (candidateFollowCompanyOptional.isPresent())
            throw new AppException(ErrorCode.CANDIDATE_FOLLOW_COMPANY_EXISTED);
        CompanyNotification companyNotification = CompanyNotification.builder().company(company).title(NotificationTitle.NOTIFICATION_COMPANY_NEW_CANDIDATE_FOLLOW.getTitle()).notification("Candidate " + candidate.getId().getUser().getName() + " has follow for your company").read(false).build();
        companyNotificationRepository.save(companyNotification);
        return CandidateFollowCompanyMapper.instance.eToDto(candidateFollowCompanyRepository.save(CandidateFollowCompany.builder().id(id).build()), candidateFollowCompanyRepository);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unFollowCompany(int candidateId, int companyId) {
        return candidateFollowCompanyRepository.deleteById(candidateId, companyId) > 0;
    }

    @Override
    public PageResponse<CompanyDto> getPageFollowedCompanies(int candidateId, Pageable pageable) {
        return pageUtils.toPageResponse(CompanyMapper.instance.ePageToDtoPage(candidateFollowCompanyRepository.getPageFollowedCompaniesByCandidateId(candidateId, pageUtils.adjustPageable(pageable)), candidateFollowCompanyRepository));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CandidateDto setDefaultResume(int candidateId, int resumeId) {
        var candidate = candidateRepository.findByUserId(candidateId);
        if(candidate.isEmpty()) throw new AppException(ErrorCode.NOT_FOUND_USER);
        var resume = resumeRepository.findById(resumeId);
        if(resume.isEmpty()) throw new AppException(ErrorCode.NOT_FOUND_RESUME);
        candidate.get().setDefaultResume(resume.get());
        return CandidateMapper.instance.eToDto(candidateRepository.save(candidate.get()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CandidateDto updatePublicProfile(int candidateId, boolean publicProfile) {
        var candidate = candidateRepository.findByUserId(candidateId);
        if(candidate.isEmpty()) throw new AppException(ErrorCode.NOT_FOUND_USER);
        candidate.get().setPublicProfile(publicProfile);
        return CandidateMapper.instance.eToDto(candidateRepository.save(candidate.get()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean candidatePickSkills(int candidateId, List<Integer> skillsId) {
        if(skillsId.size() != skillRepository.findAllByIdList(skillsId).size()){
            throw new AppException(ErrorCode.NOT_FOUND_SKILL);
        }
        Candidate candidate = candidateRepository.findByUserId(candidateId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_USER));
        candidate.setSkills(skillsId.toString());
        candidateRepository.save(candidate);
        return true;
    }

    @Override
    public List<SkillDto> getSkillOfCandidate(int candidateId) {
        Gson gson = new Gson();
        Candidate candidate = candidateRepository.findByUserId(candidateId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_USER));
        String skillString = candidate.getSkills();
        List<Integer> skillInteger = gson.fromJson(skillString, new TypeToken<List<Integer>>() {
        }.getType());
        return SkillMapper.instance.eListToDtoList(skillRepository.findAllByIdList(skillInteger));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CandidateProfileDto createCandidateSurvey(CandidateSurveyRequest request) {
        Candidate candidate = candidateRepository.findByUserId(request.getCandidateId()).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND_USER));

        Gson gson = new Gson();

        List<Skill> skills = skillRepository.findAllByIdList(request.getSkills());
        String skillJson = gson.toJson(skills);

        CandidateProfile candidateProfile = CandidateProfile.builder()
                .id(CandidateProfileId.builder()
                        .candidate(candidate)
                        .build())
                .gender(request.getGender())
                .experience(request.getExperience())
                .minSalary(request.getMinSalary())
                .maxSalary(request.getMaxSalary())
                .rank(request.getRank())
                .location(request.getLocation())
                .detailAddress(request.getDetailAddress())
                .skills(skillJson)
                .build();

        return CandidateProfileMapper.instance.eToDto(candidateProfileRepository.save(candidateProfile));
    }

    @Override
    public CandidateProfileDto editEducationInformation(int candidateId, EducationInformationDto request) {
        var candidateProfile = candidateProfileRepository.findCandidateProfileById_Candidate_Id_User_Id(candidateId);

        Gson gson = new Gson();
        String educationInfoJson = gson.toJson(request);
        if(candidateProfile.isEmpty()){
            CandidateProfile newCandidateProfile = CandidateProfile.builder()
                    .id(CandidateProfileId.builder()
                            .candidate(candidateRepository.findByUserId(candidateId).get())
                            .build())
                    .educationInfo(educationInfoJson)
                    .build();

            return CandidateProfileMapper.instance.eToDto(candidateProfileRepository.save(newCandidateProfile));
        }else {
            candidateProfile.get().setEducationInfo(educationInfoJson);
            return CandidateProfileMapper.instance.eToDto(candidateProfileRepository.save(candidateProfile.get()));
        }
    }

    @Override
    public CandidateProfileDto editExperienceInformation(int candidateId, ExperienceInformationDto request) {
        var candidateProfile = candidateProfileRepository.findCandidateProfileById_Candidate_Id_User_Id(candidateId);
        Gson gson = new Gson();
        String experienceInfoJson = gson.toJson(request);
        if(candidateProfile.isEmpty()){
            CandidateProfile newCandidateProfile = CandidateProfile.builder()
                    .id(CandidateProfileId.builder()
                            .candidate(candidateRepository.findByUserId(candidateId).get())
                            .build())
                    .experienceInfo(experienceInfoJson)
                    .build();

            return CandidateProfileMapper.instance.eToDto(candidateProfileRepository.save(newCandidateProfile));
        }else {
            candidateProfile.get().setExperienceInfo(experienceInfoJson);
            return CandidateProfileMapper.instance.eToDto(candidateProfileRepository.save(candidateProfile.get()));
        }
    }

    @Override
    public CandidateProfileDto getProfileDetail(int candidateId) {
        return CandidateProfileMapper.instance.eToDto(candidateProfileRepository.findCandidateProfileById_Candidate_Id_User_Id(candidateId).orElse(null));
    }

    @Override
    public CandidatePostDto getDetailApplyPost(int candidateId, int postId) {
        return CandidatePostMapper.instance.eToDto(candidatePostRepository.findByCandidateIdAndPostId(candidateId, postId).orElse(null), postSkillRepository, candidateFollowCompanyRepository);
    }

    @Transactional(rollbackFor = Exception.class)
    protected File uploadFile(MultipartFile multipartFile) throws Exception {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }
        Map<String, Object> options = new HashMap<>();
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), options);
            cloudinary.uploader().upload(multipartFile.getBytes(), options);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
        File file = File.builder().name(multipartFile.getOriginalFilename()).type((String) uploadResult.get("format")).size(multipartFile.getSize()).path((String) uploadResult.get("secure_url")).imageFilePath(((String) uploadResult.get("secure_url")).replace(".pdf", ".jpg")).publicId((String) uploadResult.get("public_id")).build();
        return fileRepository.save(file);
    }
}
