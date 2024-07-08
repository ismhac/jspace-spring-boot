package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.companyRequestReview.response.CompanyRequestReviewDto;
import com.ismhac.jspace.dto.product.request.ProductCreateRequest;
import com.ismhac.jspace.dto.product.request.ProductUpdateRequest;
import com.ismhac.jspace.dto.product.response.ProductDto;
import com.ismhac.jspace.dto.purchaseHistory.response.PurchaseHistoryDto;
import com.ismhac.jspace.dto.user.admin.request.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.response.AdminDto;
import com.ismhac.jspace.dto.user.request.UpdateActivatedUserRequest;
import com.ismhac.jspace.dto.user.response.UserDto;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    void initRootAdmin();

    AdminDto create(AdminCreateRequest adminCreateRequest);

    PageResponse<AdminDto> getPageAdminByTypeFilterByNameAndActivated(String name, Boolean activated, Pageable pageable);

    PageResponse<UserDto> getPageUserAndFilterByRoleIdNameAndEmailAndActivated(Integer roleId, String name, String email, Boolean activated, Pageable pageable);

    UserDto updateActivatedUser(UpdateActivatedUserRequest updateActivatedUserRequest);

    PageResponse<CompanyRequestReviewDto> getRequestReviewDtoPageResponse(Boolean reviewed, Pageable pageable);

    CompanyRequestReviewDto adminVerifyForCompany(Integer companyId, Boolean reviewed);

    PageResponse<CompanyDto> getPageCompanyAndFilter(String name, String address, String email, String phone, Boolean emailVerified, Boolean verifiedByAdmin, Pageable pageable);

    CompanyDto updateCompanyActivateStatus(int id, boolean activateStatus);

    ProductDto createProduct(ProductCreateRequest request);

    ProductDto updateProduct(int id, ProductUpdateRequest request);

    PageResponse<PurchaseHistoryDto> getPagePurchaseHistory(String companyName, String productName, Pageable pageable);

    Boolean deleteProduct(int productId);

    Boolean requestCompanyVerifyInfo(int companyId);
}
