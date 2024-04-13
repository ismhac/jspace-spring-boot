package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.user.request.UpdateActivatedUserRequest;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.dto.user.admin.request.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.response.AdminDto;
import com.ismhac.jspace.service.AdminService;
import com.ismhac.jspace.util.PageUtils;
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
    
    /* create new basic admin */
    @PostMapping()
    public ApiResponse<AdminDto> create(
            @RequestBody @Valid AdminCreateRequest adminCreateRequest) {
        var result = adminService.create(adminCreateRequest);
        return ApiResponse.<AdminDto>builder()
                .result(result)
                .build();
    }

    @GetMapping("/users")
    public ApiResponse<PageResponse<UserDto>> getPageUserAndFilterByNameAndEmailAndActivated(
            @RequestParam(value = "roleId", required = false) Integer roleId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "activated", required = false) Boolean activated,
            Pageable pageable){
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);
        var result =  adminService.getPageUserAndFilterByRoleIdNameAndEmailAndActivated(roleId, name, email, activated, adjustedPageable);
        return ApiResponse.<PageResponse<UserDto>>builder()
                .result(result)
                .build();
    }

    /* Get page basic admin, filter by name, activated */
    @GetMapping("/basic")
    public ApiResponse<PageResponse<AdminDto>> getBasicAdminPage(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "activated", required = false) Boolean activated,
            Pageable pageable) {
        Pageable adjustedPageable = pageUtils.adjustPageable(pageable);
        var result = adminService.getPageAdminByTypeFilterByNameAndActivated(name, activated, adjustedPageable);
        return ApiResponse.<PageResponse<AdminDto>>builder()
                .result(result)
                .build();
    }

    @PutMapping("/users/update/activated")
    public ApiResponse< UserDto> updateActivatedUser(
            @RequestBody @Valid UpdateActivatedUserRequest updateActivatedUserRequest){
        var result = adminService.updateActivatedUser(updateActivatedUserRequest);
        return ApiResponse.<UserDto>builder()
                .result(result)
                .build();
    }
}
