package com.ismhac.jspace.controller;

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

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("mac") String token){
        return companyService.verifyEmail(token);
    }

}
