package com.ismhac.jspace.controller;

import com.ismhac.jspace.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
