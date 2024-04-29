package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Tag(name = "Company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/verify-email")
    public void verifyEmail(
            @RequestParam("mac") String token,
            HttpServletResponse httpServletResponse) throws IOException {
        companyService.verifyEmail(token, httpServletResponse);
    }

    @GetMapping("/verify-employee")
    public void verifyEmployee(
            @RequestParam("mac") String token,
            HttpServletResponse httpServletResponse) throws IOException {
        companyService.verifyEmployee(token, httpServletResponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<CompanyDto> getCompanyById(@PathVariable("id") int id){
        var result = companyService.getCompanyById(id);
        return ApiResponse.<CompanyDto>builder()
                .result(result)
                .build();
    }
}
