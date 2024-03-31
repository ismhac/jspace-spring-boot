package com.ismhac.jspace.dto.common;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    int pageNumber;
    int pageSize;
    long totalElements;
    int totalPages;
    int numberOfElements;
    List<T> content;
}
