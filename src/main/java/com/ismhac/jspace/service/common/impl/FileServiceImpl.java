package com.ismhac.jspace.service.common.impl;

import com.cloudinary.Cloudinary;
import com.ismhac.jspace.dto.file.response.FileDto;
import com.ismhac.jspace.dto.file.response.custom.FileInfo;
import com.ismhac.jspace.mapper.FileMapper;
import com.ismhac.jspace.model.File;
import com.ismhac.jspace.repository.FileRepository;
import com.ismhac.jspace.service.common.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileDto uploadFile(MultipartFile multipartFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), new HashMap<>());

        File file = File.builder()
                .name(multipartFile.getOriginalFilename())
                .type(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .path((String) uploadResult.get("url"))
                .build();

        File savedFile = fileRepository.save(file);
        return fileMapper.toFileDto(savedFile);
    }

    @Override
    public FileInfo uploadFileInfo(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), new HashMap<>());

        FileInfo fileInfo = FileInfo.builder()
                .filePath((String) uploadResult.get("secure_url"))
                .fileId((String) uploadResult.get("public_id"))
                .build();

        return fileInfo;
    }
}
