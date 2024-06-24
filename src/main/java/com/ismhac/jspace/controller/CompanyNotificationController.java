package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.companyNotification.response.CompanyNotificationDto;
import com.ismhac.jspace.service.CompanyNotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/company-notifications")
@RequiredArgsConstructor
@Tag(name = "Company Notification")
public class CompanyNotificationController {
    private final CompanyNotificationService companyNotificationService;

    @GetMapping()
    public ApiResponse<PageResponse<CompanyNotificationDto>> getPage(@RequestParam("company_id") int companyId, Pageable pageable) {
        return ApiResponse.<PageResponse<CompanyNotificationDto>>builder().result(companyNotificationService.getCompanyNotifications(companyId, pageable)).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CompanyNotificationDto> update(@PathVariable("id") long id, @RequestParam("read") boolean read){
        return ApiResponse.<CompanyNotificationDto>builder().result(companyNotificationService.updateCompanyNotificationReadStatus(id, read)).build();
    }
}
