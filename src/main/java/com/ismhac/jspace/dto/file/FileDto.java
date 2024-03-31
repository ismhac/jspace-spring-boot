package com.ismhac.jspace.dto.file;

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
}