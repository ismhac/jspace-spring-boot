package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
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
import com.ismhac.jspace.service.AdminService;
import com.ismhac.jspace.util.PageUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@Tag(name = "Admin")
public class AdminController {
    private final PageUtils pageUtils;
    private final AdminService adminService;

    @PostMapping()
    public ApiResponse<AdminDto> create(@RequestBody @Valid AdminCreateRequest adminCreateRequest) {
        return ApiResponse.<AdminDto>builder().result(adminService.create(adminCreateRequest)).build();
    }

    @GetMapping("/users")
    public ApiResponse<PageResponse<UserDto>> getPageUserAndFilterByNameAndEmailAndActivated(@RequestParam(value = "roleId", required = false) Integer roleId, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "activated", required = false) Boolean activated, Pageable pageable) {
        return ApiResponse.<PageResponse<UserDto>>builder().result(adminService.getPageUserAndFilterByRoleIdNameAndEmailAndActivated(roleId, name, email, activated, pageUtils.adjustPageable(pageable))).build();
    }

    @Hidden
    @GetMapping("/basic")
    public ApiResponse<PageResponse<AdminDto>> getBasicAdminPage(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "activated", required = false) Boolean activated, Pageable pageable) {
        return ApiResponse.<PageResponse<AdminDto>>builder().result(adminService.getPageAdminByTypeFilterByNameAndActivated(name, activated, pageUtils.adjustPageable(pageable))).build();
    }

    @PutMapping("/users/update/activated")
    public ApiResponse<UserDto> updateActivatedUser(@RequestBody @Valid UpdateActivatedUserRequest updateActivatedUserRequest) {
        return ApiResponse.<UserDto>builder().result(adminService.updateActivatedUser(updateActivatedUserRequest)).build();
    }

    @GetMapping("/company-request-reviews")
    public ApiResponse<PageResponse<CompanyRequestReviewDto>> getRequestReviewDtoPageResponse(@RequestParam(value = "reviewed", required = false) Boolean reviewed, Pageable pageable) {
        return ApiResponse.<PageResponse<CompanyRequestReviewDto>>builder().result(adminService.getRequestReviewDtoPageResponse(reviewed, pageUtils.adjustPageable(pageable))).build();
    }

    @PutMapping("/company-request-reviews")
    public ApiResponse<CompanyRequestReviewDto> adminVerifyForCompany(@RequestParam("companyId") Integer companyId, @RequestParam("reviewed") Boolean reviewed) {
        return ApiResponse.<CompanyRequestReviewDto>builder().result(adminService.adminVerifyForCompany(companyId, reviewed)).build();
    }

    @GetMapping("/companies")
    public ApiResponse<PageResponse<CompanyDto>> getPageCompanyAndFilter(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "address", required = false) String address, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "emailVerified", required = false) Boolean emailVerified, @RequestParam(value = "verifiedByAdmin", required = false) Boolean verifiedByAdmin, Pageable pageable) {
        return ApiResponse.<PageResponse<CompanyDto>>builder().result(adminService.getPageCompanyAndFilter(name, address, email, phone, emailVerified, verifiedByAdmin, pageUtils.adjustPageable(pageable))).build();
    }

    @PutMapping("/companies/{id}/update-activate-status")
    public ApiResponse<CompanyDto> updateCompanyActivateStatus(@PathVariable("id") int id, @RequestParam("activateStatus") boolean activateStatus) {
        var result = adminService.updateCompanyActivateStatus(id, activateStatus);
        return ApiResponse.<CompanyDto>builder().result(result).build();
    }

    @PostMapping("/products")
    public ApiResponse<ProductDto> createProduct(@RequestBody ProductCreateRequest request) {
        return ApiResponse.<ProductDto>builder().result(adminService.createProduct(request)).build();
    }

    @PatchMapping("/products/{id}")
    public ApiResponse<ProductDto> updateProduct(@PathVariable("id") int id, @RequestBody ProductUpdateRequest request) {
        return ApiResponse.<ProductDto>builder().result(adminService.updateProduct(id, request)).build();
    }

    @GetMapping("/purchase-histories")
    public ApiResponse<PageResponse<PurchaseHistoryDto>> getPagePurchaseHistory(@RequestParam(value = "companyName", required = false) String companyName, @RequestParam(value = "productName", required = false) String productName, Pageable pageable) {
        return ApiResponse.<PageResponse<PurchaseHistoryDto>>builder().result(adminService.getPagePurchaseHistory(companyName, productName, pageUtils.adjustPageable(pageable))).build();
    }
}
