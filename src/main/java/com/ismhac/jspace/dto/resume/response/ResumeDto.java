package com.ismhac.jspace.dto.resume.response;

import com.ismhac.jspace.dto.file.response.FileDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeDto {

    int id;

    String name;

    int candidateId;

    FileDto file;
}
