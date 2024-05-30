package com.ismhac.jspace.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ismhac.jspace.dto.candidatePost.request.CandidatePostCreateRequest;
import com.ismhac.jspace.dto.candidatePost.response.CandidatePostDto;
import com.ismhac.jspace.dto.candidatePostLiked.response.CandidatePostLikedDto;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.post.PostDto;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.user.candidate.request.CandidateUpdateRequest;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.mapper.*;
import com.ismhac.jspace.model.*;
import com.ismhac.jspace.model.enums.ApplyStatus;
import com.ismhac.jspace.model.primaryKey.CandidateId;
import com.ismhac.jspace.model.primaryKey.CandidatePostId;
import com.ismhac.jspace.model.primaryKey.CandidatePostLikedId;
import com.ismhac.jspace.repository.*;
import com.ismhac.jspace.service.CandidateService;
import com.ismhac.jspace.util.BeanUtils;
import com.ismhac.jspace.util.PageUtils;
import com.ismhac.jspace.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private UserRepository userRepository;

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

    @Autowired
    private BeanUtils beanUtils;

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

            Candidate candidate = candidateRepository.findById(CandidateId.builder()
                    .user(user)
                    .build()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));

            File uploadedFile = uploadFile(file);
            Resume resume = Resume.builder()
                    .name(name)
                    .candidate(candidate)
                    .file(uploadedFile)
                    .build();

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

        Candidate candidate = candidateRepository.findByUserId(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

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
        Candidate candidate = candidateRepository.findByUserId(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

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
        Candidate candidate = candidateRepository.findByUserId(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

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

            Resume resume = resumeRepository.findById(resumeId)
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_RESUME));

            Map deleteResult = cloudinary.uploader().destroy(resume.getFile().getPublicId(), ObjectUtils.emptyMap());

            if (deleteResult.get("result").toString().equals("ok")) {

                resumeRepository.deleteById(resumeId);
                fileRepository.deleteById(resume.getFile().getId());

                return new HashMap<>() {{
                    put("status", deleteResult);
                }};
            } else {
                return new HashMap<>() {{
                    put("status", deleteResult);
                }};
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.DELETE_FILE_FAIL);
        }
    }

    @Override
    public CandidatePostLikedDto likePost(int id, int postId) {

        Candidate candidate = candidateRepository.findByUserId(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_POST));

        CandidatePostLikedId candidatePostLikedId = CandidatePostLikedId.builder()
                .candidate(candidate)
                .post(post)
                .build();

        CandidatePostLiked candidatePostLiked = CandidatePostLiked.builder()
                .id(candidatePostLikedId)
                .build();

        return CandidatePostLikedMapper.instance.eToDto(candidatePostLikedRepository.save(candidatePostLiked), postSkillRepository);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlikePost(int id, int postId) {
        CandidatePostLiked candidatePostLiked = candidatePostLikedRepository
                .findByCandiDateIdAndPostId(id, postId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_POST_LIKED));

        int deleted = candidatePostLikedRepository.deleteByCandidateIdAndPostId(id, postId);
        if(deleted == 1) return true;
        return false;
    }

    @Override
    public PageResponse<PostDto> getPagePostLiked(int id, Pageable pageable) {
        Page<Post> posts = candidatePostLikedRepository.getPagePostByCandidateId(id, pageable);
        return pageUtils.toPageResponse(PostMapper.instance.ePageToDtoPage(posts, postSkillRepository));
    }

    @Override
    public PageResponse<Map<String, Object>> getPagePost(int id, Pageable pageable) {
        Page<Map<String, Object>> postPage = postRepository.candidateGetPagePost(id, pageable);

        List<Map<String, Object>> dtoList = postPage.getContent().stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>(item);
                    map.put("post", PostMapper.instance.eToDto((Post) item.get("post"), postSkillRepository));
                    return map;
                }).toList();

        return PageResponse.<Map<String, Object>>builder()
                .content(dtoList)
                .pageNumber(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPages(postPage.getTotalPages())
                .numberOfElements(postPage.getNumberOfElements())
                .build();
    }

    @Override
    public CandidatePostDto applyPost(CandidatePostCreateRequest request) {

        Optional<CandidatePost> candidatePostOptional = candidatePostRepository
                .findByCandidateIdAndPostId(request.getCandidateId(), request.getPostId());

        if(candidatePostOptional.isPresent()){
            throw new AppException(ErrorCode.HAS_APPLIED);
        }

        Candidate candidate = candidateRepository.findByUserId(request.getCandidateId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_POST));
        Resume resume = resumeRepository.findById(request.getResumeId())
                .orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_RESUME));

        CandidatePost candidatePost = CandidatePost.builder()
                .id(CandidatePostId.builder()
                        .candidate(candidate)
                        .post(post)
                        .build())
                .resume(resume)
                .applyStatus(ApplyStatus.PROGRESS)
                .build();
        return CandidatePostMapper.instance.eToDto(candidatePostRepository.save(candidatePost), postSkillRepository);
    }


    @Transactional(rollbackFor = Exception.class)
    protected File uploadFile(MultipartFile multipartFile) throws Exception {

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        Map<String, Object> options = new HashMap<>();
//
        Map uploadResult;

        try {
            uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), options);
            cloudinary.uploader().upload(multipartFile.getBytes(), options);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

        log.info("----- upload result : {}", uploadResult);

        File file = File.builder()
                .name(multipartFile.getOriginalFilename())
                .type((String) uploadResult.get("format"))
                .size(multipartFile.getSize())
                .path((String) uploadResult.get("secure_url"))
                .imageFilePath(((String) uploadResult.get("secure_url")).replace(".pdf", ".jpg"))
                .publicId((String) uploadResult.get("public_id"))
                .build();

        File savedFile = fileRepository.save(file);
        return savedFile;
    }
}
