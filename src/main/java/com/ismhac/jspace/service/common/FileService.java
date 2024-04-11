package com.ismhac.jspace.service.common;

import com.ismhac.jspace.dto.file.FileDto;
import com.ismhac.jspace.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileDto uploadFile(MultipartFile multipartFile) throws IOException;
}
