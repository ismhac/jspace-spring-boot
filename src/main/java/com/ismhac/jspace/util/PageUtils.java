package com.ismhac.jspace.util;

import com.ismhac.jspace.dto.common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {
    public <T> PageResponse<T> toPageResponse(Page<T> page) {
        return PageResponse.<T>builder()
                .pageNumber(page.getPageable().getPageNumber())
                .pageSize(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .numberOfElements(page.getNumberOfElements())
                .content(page.getContent())
                .build();
    }
}
