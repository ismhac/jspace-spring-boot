package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.file.response.FileDto;
import com.ismhac.jspace.dto.file.response.custom.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileDto uploadFile(MultipartFile multipartFile) throws IOException;

    FileInfo uploadFileInfo(MultipartFile file) throws IOException;
}
