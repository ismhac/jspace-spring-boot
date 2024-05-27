package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.product.response.ProductDto;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    PageResponse<ProductDto> getPage(Pageable pageable);

    ProductDto getById(int productId);
}
