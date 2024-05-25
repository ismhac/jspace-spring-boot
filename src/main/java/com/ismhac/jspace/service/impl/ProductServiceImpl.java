package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.mapper.ProductMapper;
import com.ismhac.jspace.model.Product;
import com.ismhac.jspace.repository.ProductRepository;
import com.ismhac.jspace.service.ProductService;
import com.ismhac.jspace.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PageUtils pageUtils;

    @Override
    public PageResponse<ProductDto> getPage(Pageable pageable) {
        Page<Product> productPage = productRepository.getPage(pageable);
        return pageUtils.toPageResponse(ProductMapper.instance.ePageToDtoPage(productPage));
    }
}
