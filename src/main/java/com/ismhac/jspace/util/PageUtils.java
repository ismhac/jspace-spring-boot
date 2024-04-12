package com.ismhac.jspace.util;

import com.ismhac.jspace.dto.common.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {
    public <T> PageResponse<T> toPageResponse(Page<T> page) {
        return PageResponse.<T>builder()
                .pageNumber(page.getPageable().getPageNumber() + 1)
                .pageSize(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .numberOfElements(page.getNumberOfElements())
                .content(page.getContent())
                .build();
    }

    public Pageable adjustPageable(Pageable pageable) {
        return PageRequest.of(Math.max(pageable.getPageNumber() - 1, 0), pageable.getPageSize(), pageable.getSort());
    }
}
