package com.ismhac.jspace.controller;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.dto.common.response.PageResponse;
import com.ismhac.jspace.dto.company.request.CompanyCreateRequest;
import com.ismhac.jspace.dto.company.response.CompanyDto;
import com.ismhac.jspace.dto.user.employee.request.EmployeeUpdateRequest;
import com.ismhac.jspace.dto.user.employee.response.EmployeeDto;
import com.ismhac.jspace.dto.user.response.UserDto;
import com.ismhac.jspace.service.EmployeeService;
import com.ismhac.jspace.util.PageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
}
