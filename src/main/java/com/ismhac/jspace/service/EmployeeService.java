package com.ismhac.jspace.service;

import com.ismhac.jspace.dto.cart.request.CartCreateRequest;
import com.ismhac.jspace.dto.cart.response.CartDto;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.post.request.PostCreateRequest;
import com.ismhac.jspace.dto.post.request.PostUpdateRequest;
import com.ismhac.jspace.dto.post.response.PostDto;
import com.ismhac.jspace.dto.purchaseHistory.response.PurchaseHistoryDto;
import com.ismhac.jspace.dto.user.employee.request.EmployeeUpdateRequest;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    PageResponse<EmployeeDto> getPageByCompanyIdFilterByEmailAndName(int companyId, String email, String name, Pageable pageable);

    UserDto update(int id, EmployeeUpdateRequest request);

    PageResponse<CompanyDto> getPageCompany(String name, String address, Pageable pageable);

    CompanyDto createCompany(CompanyCreateRequest request);

    String employeePickCompany(Integer companyId);

    EmployeeDto updateBackground(int id, MultipartFile background);

    PostDto createPost(PostCreateRequest req);

    EmployeeDto updateAvatar(int id, MultipartFile avatar);

    Map<String, Object> deleteBackground(int id, String backgroundId);

    Map<String, Object> deleteAvatar(int id, String avatarId);

    List<Map<String, Object>> getListPurchasedByCompanyId(int companyId);

    PageResponse<PostDto> getPagePosted(int companyId, Pageable pageable);

    CartDto addProductToCart(CartCreateRequest request);

    CartDto updateCart(int cartId, int quantity);

    PageResponse<CartDto> getCarts(int companyId, Pageable pageable);

    String deleteCart(int cartId);

    PageResponse<PurchaseHistoryDto> getPageAndFilterByProductName(int companyId, String productName, Pageable pageable);

    PostDto updatePost(int postId, PostUpdateRequest request);
 }
