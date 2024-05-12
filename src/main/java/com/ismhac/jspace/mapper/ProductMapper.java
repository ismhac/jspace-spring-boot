package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.product.request.ProductCreateRequest;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper instance = Mappers.getMapper(ProductMapper.class);

    Product createReqToE(ProductCreateRequest request);

    ProductDto eToDto(Product e);

    default  Page<ProductDto> ePageToDtoPage(Page<Product> ePage){
        return ePage.map(this::eToDto);
    }
}
