package com.ismhac.jspace.service.impl;

import com.cloudinary.Cloudinary;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.dto.user.candidate.request.CandidateUpdateRequest;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.EmployeeMapper;
import com.ismhac.jspace.mapper.ResumeMapper;
import com.ismhac.jspace.mapper.UserMapper;
import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.File;
import com.ismhac.jspace.model.Resume;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.primaryKey.CandidateId;
import com.ismhac.jspace.repository.CandidateRepository;
import com.ismhac.jspace.repository.FileRepository;
import com.ismhac.jspace.repository.ResumeRepository;
import com.ismhac.jspace.repository.UserRepository;
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
import java.util.HashMap;
import java.util.Map;

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
    public UserDto updateAvatar(int id, MultipartFile avatar) {
        Candidate candidate = candidateRepository.findByUserId(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

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
    public UserDto updateBackground(int id, MultipartFile avatar) {
        Candidate candidate = candidateRepository.findByUserId(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

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
