package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.service.ProductService;
import com.ismhac.jspace.util.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;
    private final PageUtils pageUtils;

    @GetMapping()
    public ApiResponse<PageResponse<ProductDto>> getAll(org.springframework.data.domain.Pageable pageable) {
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);
        var result = productService.getPage(adjustedPageable);
        return ApiResponse.<PageResponse<ProductDto>>builder()
                .result(result)
                .build();
    }
}