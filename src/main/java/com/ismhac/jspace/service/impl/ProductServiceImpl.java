package com.ismhac.jspace.service.impl;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.exception.AppException;
import com.ismhac.jspace.exception.ErrorCode;
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
    public PageResponse<ProductDto> getPage(String name,Pageable pageable) {
        Page<Product> productPage = productRepository.getPage(name,pageable);
        return pageUtils.toPageResponse(ProductMapper.instance.ePageToDtoPage(productPage));
    }

    @Override
    public ProductDto getById(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
        if(product.getDeleted()) throw new AppException(ErrorCode.NOT_FOUND_PRODUCT);
        return ProductMapper.instance.eToDto(product);
    }
}
