package com.ismhac.jspace.dto.resume;

import com.ismhac.jspace.dto.file.FileDto;
import com.ismhac.jspace.model.Candidate;
import com.ismhac.jspace.model.File;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
