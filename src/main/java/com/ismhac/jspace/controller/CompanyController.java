package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.ApiResponse;
import com.ismhac.jspace.dto.company.CompanyDto;
import com.ismhac.jspace.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    @GetMapping()
    public ApiResponse<Page<CompanyDto>> getPage(
            @RequestParam(value = "type", defaultValue = "all") String type,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize) {

        Page<CompanyDto> result = new PageImpl<>(new ArrayList<>());

        if (type.equals("has")) {
            result = companyService.getPageHasPost(name, address, pageNumber, pageSize);
        } else if (type.equals("no")) {
            result = companyService.getPageNoPost(name, address, pageNumber, pageSize);
        } else {
            result = companyService.getPage(name, address, pageNumber, pageSize);
        }

        return ApiResponse.<Page<CompanyDto>>builder()
                .result(result)
                .build();
    }


}
