package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.common.PageResponse;
import com.ismhac.jspace.dto.user.admin.AdminCreateRequest;
import com.ismhac.jspace.dto.user.admin.AdminDto;
import com.ismhac.jspace.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@Tag(name = "Admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping()
    public ApiResponse<AdminDto> create(@RequestBody @Valid AdminCreateRequest adminCreateRequest){
        var result = adminService.create(adminCreateRequest);
        return ApiResponse.<AdminDto>builder()
                .result(result)
                .build();
    }

    @GetMapping("/basic")
    public ApiResponse<PageResponse<AdminDto>> getBasicAdminPage(
            @RequestParam("name") String name,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize){
        var result = adminService.getPageAdmin(name, pageNumber, pageSize);
        return ApiResponse.<PageResponse<AdminDto>>builder()
                .result(result)
                .build();
    }
}
