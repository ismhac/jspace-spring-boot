package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.other.ProductTypeDto;
import com.ismhac.jspace.dto.product.request.ProductCreateRequest;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.model.Product;
import com.ismhac.jspace.model.enums.ProductType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper instance = Mappers.getMapper(ProductMapper.class);

    Product createReqToE(ProductCreateRequest request);

    @Mapping(target = "productType", source = "productType", qualifiedByName = "convertProductTypeToDto")
    ProductDto eToDto(Product e);

    default  Page<ProductDto> ePageToDtoPage(Page<Product> ePage){
        return ePage.map(this::eToDto);
    }

    @Named("convertProductTypeToDto")
    default ProductTypeDto convertProductTypeToDto(ProductType productType){
        String description;
        if(productType.equals(ProductType.TYPE_1)){
            description = "Dịch vụ đăng bài tuyển dụng";
        }
        else{
            description = "Dịch vụ tìm kiếm ứng viên";
        }
        return ProductTypeDto.builder()
                .productType(productType)
                .description(description)
                .build();
    }
}
