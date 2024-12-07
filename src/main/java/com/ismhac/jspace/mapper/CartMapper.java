package com.ismhac.jspace.mapper;

import com.ismhac.jspace.dto.cart.response.CartDto;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.model.Cart;
import com.ismhac.jspace.model.Company;
import com.ismhac.jspace.model.Product;
import com.ismhac.jspace.repository.CandidateFollowCompanyRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper instance = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "company", source = "company", qualifiedByName = "convertCompanyToDto")
    @Mapping(target ="product", source = "product", qualifiedByName = "convertProductToDto")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPrice(e.getProduct(), e.getQuantity()))")
    CartDto eToDto(Cart e, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository);

    default Page<CartDto> ePageToDtoPage(Page<Cart> ePage, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return ePage.map(item -> this.eToDto(item,candidateFollowCompanyRepository ));
    }

    @Named("convertCompanyToDto")
    default CompanyDto convertCompanyToDto(Company company, @Context CandidateFollowCompanyRepository candidateFollowCompanyRepository){
        return CompanyMapper.instance.eToDto(company, candidateFollowCompanyRepository);
    }

    @Named("convertProductToDto")
    default ProductDto convertProductToDto(Product product){
        return ProductMapper.instance.eToDto(product);
    }

    default Double calculateTotalPrice(Product product, int quantity){
        return quantity * product.getPrice();
    }
}
