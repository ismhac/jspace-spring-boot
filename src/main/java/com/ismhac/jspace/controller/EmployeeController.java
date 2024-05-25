package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.post.PostCreateRequest;
import com.ismhac.jspace.dto.post.PostDto;
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
    ApiResponse<PageResponse<EmployeeDto>> getPage(
            @RequestParam("company_id") int companyId,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            Pageable pageable) {

        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);

        var result = employeeService.getPageByCompanyIdFilterByEmailAndName(companyId, email, name, adjustedPageable);

        return ApiResponse.<PageResponse<EmployeeDto>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/companies")
    public ApiResponse<PageResponse<CompanyDto>> getPageCompany(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "address", required = false) String address,
            Pageable pageable){
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);

        var result = employeeService.getPageCompany(name, address, adjustedPageable);
        return ApiResponse.<PageResponse<CompanyDto>>builder()
                .result(result)
                .build();
    }


    @PostMapping("/companies")
    public ApiResponse<CompanyDto> createCompany(
            @RequestBody CompanyCreateRequest request){
        var result = employeeService.createCompany(request);
        return ApiResponse.<CompanyDto>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserDto> update(
            @PathVariable("id") int id,
            @RequestBody EmployeeUpdateRequest request) {
        var result = employeeService.update(id, request);

        return ApiResponse.<UserDto>builder()
                .result(result)
                .build();
    }

    @PutMapping("/pick-company")
    public ApiResponse<String> employeePickCompany(
            @RequestParam("companyId") Integer companyId) {
        var result = employeeService.employeePickCompany(companyId);
        return ApiResponse.<String>builder()
                .result(result)
                .build();
    }

    @PutMapping(value = "/{id}/update-background",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ApiResponse<EmployeeDto> updateBackground(
            @PathVariable("id") int id,
            @RequestParam("file") MultipartFile background){
        var result = employeeService.updateBackground(id, background);
        return ApiResponse.<EmployeeDto>builder()
                .result(result)
                .build();
    }

    @PostMapping("/posts")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<PostDto> createPost(
            @RequestBody PostCreateRequest req){
        var result = employeeService.createPost(req);
        return ApiResponse.<PostDto>builder()
                .result(result)
                .build();
    }

    @PutMapping(value = "/{id}/update-avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ApiResponse<EmployeeDto> updateAvatar(
            @PathVariable("id") int id,
            @RequestParam("file") MultipartFile avatar){
        var result = employeeService.updateAvatar(id, avatar);
        return ApiResponse.<EmployeeDto>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}/delete-avatar")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ApiResponse<Map<String, Object>> deleteAvatar(
            @PathVariable("id") int id,
            @RequestParam("avatarId") String avatarId){
        var result = employeeService.deleteAvatar(id, avatarId);
        return ApiResponse.<Map<String, Object>>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{id}/delete-background")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ApiResponse<Map<String, Object>> deleteBackground(
            @PathVariable("id") int id,
            @RequestParam("backgroundId") String backgroundId){
        var result = employeeService.deleteBackground(id, backgroundId);
        return ApiResponse.<Map<String, Object>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/purchased-products")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<List<Map<String, Object>>> getListByCompanyId(
            @RequestParam("companyId") int companyId){
        var result = employeeService.getListPurchasedByCompanyId(companyId);
        return ApiResponse.<List<Map<String, Object>>>builder()
                .result(result)
                .build();
    }
}
