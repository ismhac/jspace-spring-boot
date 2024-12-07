package com.ismhac.jspace.dto.file.response.custom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfo {
    String filePath;
    String fileId;
}
