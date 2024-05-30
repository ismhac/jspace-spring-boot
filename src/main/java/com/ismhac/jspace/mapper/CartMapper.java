package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.cart.response.CartDto;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.model.Cart;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper instance = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "company", source = "company", qualifiedByName = "convertCompanyToDto")
    @Mapping(target ="product", source = "product", qualifiedByName = "convertProductToDto")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPrice(e.getProduct(), e.getQuantity()))")
    CartDto eToDto(Cart e);

    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company company){
        return CompanyMapper.instance.eToDto(company);
    }

    @Named("convertProductToDto")
    default ProductDto convertProductToDto(Product product){
        return ProductMapper.instance.eToDto(product);
    }

    default Double calculateTotalPrice(Product product, int quantity){
        return quantity * product.getPrice();
    }
}
