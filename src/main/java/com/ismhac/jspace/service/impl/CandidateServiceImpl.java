package com.ismhac.jspace.service.impl;

import com.cloudinary.Cloudinary;
import com.ismhac.jspace.dto.resume.request.ResumeCreateRequest;
import com.ismhac.jspace.dto.resume.response.ResumeDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.mapper.ResumeMapper;
import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.File;
import com.ismhac.jspace.model.Resume;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.primaryKey.CandidateId;
import com.ismhac.jspace.repository.CandidateRepository;
import com.ismhac.jspace.repository.FileRepository;
import com.ismhac.jspace.repository.ResumeRepository;
import com.ismhac.jspace.service.CandidateService;
import com.ismhac.jspace.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ResumeRepository resumeRepository;
    private final UserUtils userUtils;
    private final CandidateRepository candidateRepository;

    private final ResumeMapper resumeMapper;

    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResumeDto createResume(ResumeCreateRequest resumeCreateRequest, MultipartFile file) {
        try {
            User user = userUtils.getUserFromToken();

            Candidate candidate = candidateRepository.findById(CandidateId.builder()
                    .user(user)
                    .build()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));

            File uploadedFile = uploadFile(file);
            Resume resume = Resume.builder()
                    .name(resumeCreateRequest.getName())
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
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

        log.info("----- upload result : {}", uploadResult);

        File file = File.builder()
                .name(multipartFile.getOriginalFilename())
                .type((String) uploadResult.get("format"))
                .size(multipartFile.getSize())
                .path((String) uploadResult.get("secure_url"))
                .publicId((String) uploadResult.get("public_id"))
                .build();

        File savedFile = fileRepository.save(file);
        return savedFile;
    }
}
