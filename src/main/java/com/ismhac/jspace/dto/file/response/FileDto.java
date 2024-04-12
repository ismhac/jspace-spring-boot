package com.ismhac.jspace.dto.file.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileDto {
    int id;
    String name;
    String type;
    long size;
    String path;
    String publicId;
}