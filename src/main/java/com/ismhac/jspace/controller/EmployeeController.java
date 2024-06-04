package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.cart.request.CartCreateRequest;
import com.ismhac.jspace.dto.cart.response.CartDto;
import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.post.request.PostCreateRequest;
import com.ismhac.jspace.dto.post.request.PostUpdateRequest;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.purchaseHistory.response.PurchaseHistoryDto;
import com.ismhac.jspace.dto.purchasedProduct.response.PurchasedProductDto;
import com.ismhac.jspace.dto.user.employee.request.EmployeeUpdateRequest;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.service.EmployeeService;
import com.ismhac.jspace.util.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Employee")
public class EmployeeController {
    private final PageUtils pageUtils;
    private final EmployeeService employeeService;

    @GetMapping()
    ApiResponse<PageResponse<EmployeeDto>> getPage(@RequestParam("company_id") int companyId, @RequestParam("email") String email, @RequestParam("name") String name, Pageable pageable) {
        return ApiResponse.<PageResponse<EmployeeDto>>builder().result(employeeService.getPageByCompanyIdFilterByEmailAndName(companyId, email, name, pageUtils.adjustPageable(pageable))).build();
    }

    @GetMapping("/companies")
    public ApiResponse<PageResponse<CompanyDto>> getPageCompany(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "address", required = false) String address, Pageable pageable) {
        return ApiResponse.<PageResponse<CompanyDto>>builder().result(employeeService.getPageCompany(name, address, pageUtils.adjustPageable(pageable))).build();
    }

    @PostMapping("/companies")
    public ApiResponse<CompanyDto> createCompany(@RequestBody CompanyCreateRequest request) {
        return ApiResponse.<CompanyDto>builder().result(employeeService.createCompany(request)).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserDto> update(@PathVariable("id") int id, @RequestBody EmployeeUpdateRequest request) {
        return ApiResponse.<UserDto>builder().result(employeeService.update(id, request)).build();
    }

    @PutMapping("/pick-company")
    public ApiResponse<String> employeePickCompany(@RequestParam("companyId") Integer companyId) {
        return ApiResponse.<String>builder().result(employeeService.employeePickCompany(companyId)).build();
    }

    @PutMapping(value = "/{id}/update-background", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ApiResponse<EmployeeDto> updateBackground(@PathVariable("id") int id, @RequestParam("file") MultipartFile background) {
        return ApiResponse.<EmployeeDto>builder().result(employeeService.updateBackground(id, background)).build();
    }

    @PostMapping("/posts")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<PostDto> createPost(@RequestBody PostCreateRequest req) {
        return ApiResponse.<PostDto>builder().result(employeeService.createPost(req)).build();
    }

    @PutMapping(value = "/{id}/update-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ApiResponse<EmployeeDto> updateAvatar(@PathVariable("id") int id, @RequestParam("file") MultipartFile avatar) {
        return ApiResponse.<EmployeeDto>builder().result(employeeService.updateAvatar(id, avatar)).build();
    }

    @DeleteMapping("/{id}/delete-avatar")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ApiResponse<Map<String, Object>> deleteAvatar(@PathVariable("id") int id, @RequestParam("avatarId") String avatarId) {
        return ApiResponse.<Map<String, Object>>builder().result(employeeService.deleteAvatar(id, avatarId)).build();
    }

    @DeleteMapping("/{id}/delete-background")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ApiResponse<Map<String, Object>> deleteBackground(@PathVariable("id") int id, @RequestParam("backgroundId") String backgroundId) {
        return ApiResponse.<Map<String, Object>>builder().result(employeeService.deleteBackground(id, backgroundId)).build();
    }

    @GetMapping("/purchased-products")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<List<Map<String, Object>>> getListByCompanyId(@RequestParam("companyId") int companyId) {
        return ApiResponse.<List<Map<String, Object>>>builder().result(employeeService.getListPurchasedByCompanyId(companyId)).build();
    }

    @GetMapping("/posts")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<PageResponse<PostDto>> getPagePosted(@RequestParam("companyId") int companyId, Pageable pageable) {
        return ApiResponse.<PageResponse<PostDto>>builder().result(employeeService.getPagePosted(companyId, pageUtils.adjustPageable(pageable))).build();
    }

    @PostMapping("/carts")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<CartDto> addProductToCart(@RequestBody CartCreateRequest request) {
        return ApiResponse.<CartDto>builder().result(employeeService.addProductToCart(request)).build();
    }

    @PutMapping("/carts")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<CartDto> addProductToCart(@RequestParam("cartId") int cartId, @RequestParam("quantity") int quantity) {
        return ApiResponse.<CartDto>builder().result(employeeService.updateCart(cartId, quantity)).build();
    }

    @DeleteMapping("/carts")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<String> DeleteProductFromCart(@RequestParam("cartId") int cartId) {
        return ApiResponse.<String>builder().result(employeeService.deleteCart(cartId)).build();
    }

    @GetMapping("/carts")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<PageResponse<CartDto>> getCarts(@RequestParam("companyId") int companyId, Pageable pageable) {
        return ApiResponse.<PageResponse<CartDto>>builder().result(employeeService.getCarts(companyId, pageUtils.adjustPageable(pageable))).build();
    }

    @GetMapping("/purchase-history")
    public ApiResponse<PageResponse<PurchaseHistoryDto>> getPageAndFilterByProductName(@RequestParam("companyId") int companyId, @RequestParam(value = "productName", required = false) String productName, Pageable pageable) {
        return ApiResponse.<PageResponse<PurchaseHistoryDto>>builder().result(employeeService.getPageAndFilterByProductName(companyId, productName, pageUtils.adjustPageable(pageable))).build();
    }

    @PutMapping("/posts/{postId}")
    public ApiResponse<PostDto> updatePost(@PathVariable("postId") int postId, @RequestBody PostUpdateRequest request) {
        return ApiResponse.<PostDto>builder().result(employeeService.updatePost(postId, request)).build();
    }

    @GetMapping("/purchased-products/{purchasedProductId}")
    public ApiResponse<PurchasedProductDto> getPurchasedProductById(@RequestParam("companyId") int companyId, @PathVariable("purchasedProductId") int purchasedProductId) {
        return ApiResponse.<PurchasedProductDto>builder().result(employeeService.getPurchasedProductById(companyId, purchasedProductId)).build();
    }
}
