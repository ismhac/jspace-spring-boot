package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.file.response.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileDto uploadFile(MultipartFile multipartFile) throws IOException;
}
