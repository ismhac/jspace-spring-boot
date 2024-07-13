package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.service.ProductService;
import com.ismhac.jspace.util.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;
    private final PageUtils pageUtils;

    @GetMapping()
    public ApiResponse<PageResponse<ProductDto>> getAll(@RequestParam("name") String name, Pageable pageable) {
        return ApiResponse.<PageResponse<ProductDto>>builder().result(productService.getPage(name,pageUtils.adjustPageable(pageable))).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDto> getById(@PathVariable("id") int id) {
        return ApiResponse.<ProductDto>builder().result(productService.getById(id)).build();
    }
}
