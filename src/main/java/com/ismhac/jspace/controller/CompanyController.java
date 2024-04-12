package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Tag(name ="Company")
public class CompanyController {

    private final CompanyService companyService;
    @GetMapping()
    public ApiResponse<PageResponse<CompanyDto>> getPage(
            @RequestParam(value = "type", defaultValue = "all") String type,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize) {

        PageResponse<CompanyDto> result;

        if (type.equals("has")) {
            result = companyService.getPageHasPost(name, address, pageNumber, pageSize);
        } else if (type.equals("no")) {
            result = companyService.getPageNoPost(name, address, pageNumber, pageSize);
        } else {
            result = companyService.getPage(name, address, pageNumber, pageSize);
        }

        return ApiResponse.<PageResponse<CompanyDto>>builder()
                .result(result)
                .build();
    }
}
