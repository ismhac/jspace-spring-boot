package com.ismhac.jspace.controller.dashboard;

import com.ismhac.jspace.dto.common.response.ApiResponse;
import com.ismhac.jspace.service.dashboard.AdminDashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboards")
@RequiredArgsConstructor
@Tag(name = "Dashboard")
public class DashboardController {
    private final AdminDashboardService adminDashboardService;

    @GetMapping("/admins/users/statistic-month")
    public ApiResponse<Map<Integer, Integer>> userStatisticByMonth(@RequestParam("year") int year, @RequestParam("month") int month){
        return ApiResponse.<Map<Integer, Integer>>builder().result(adminDashboardService.getUserCountsForMonth(year, month)).build();
    }

    @GetMapping("/admins/users/statistic-year")
    public ApiResponse<Map<Integer, Integer>> userStatisticByYear(@RequestParam("year") int year){
        return ApiResponse.<Map<Integer, Integer>>builder().result(adminDashboardService.getUserCountsForYear(year)).build();
    }

    @GetMapping("/admins/posts/statistic-month")
    public ApiResponse<Map<Integer, Integer>> postStatisticByMonth(@RequestParam("year") int year, @RequestParam("month") int month){
        return ApiResponse.<Map<Integer, Integer>>builder().result(adminDashboardService.getPostCountsForMonth(year, month)).build();
    }

    @GetMapping("/admins/posts/statistic-year")
    public ApiResponse<Map<Integer, Integer>> postStatisticByYear(@RequestParam("year") int year){
        return ApiResponse.<Map<Integer, Integer>>builder().result(adminDashboardService.getPostCountsForYear(year)).build();
    }

    @GetMapping("/admins/companies/statistic-month")
    public ApiResponse<Map<Integer, Integer>> companyStatisticByMonth(@RequestParam("year") int year, @RequestParam("month") int month){
        return ApiResponse.<Map<Integer, Integer>>builder().result(adminDashboardService.getCompanyCountsForMonth(year, month)).build();
    }

    @GetMapping("/admins/companies/statistic-year")
    public ApiResponse<Map<Integer, Integer>> companyStatisticByYear(@RequestParam("year") int year){
        return ApiResponse.<Map<Integer, Integer>>builder().result(adminDashboardService.getCompanyCountsForYear(year)).build();
    }

    @GetMapping("/companies/posts/statistic-month")
    public ApiResponse<Map<Integer, Integer>> postStatisticByMonthOfCompany(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("companyId") int companyId){
        return ApiResponse.<Map<Integer, Integer>>builder().result(adminDashboardService.getPostCountsForMonthOfCompany(year, month, companyId)).build();
    }

    @GetMapping("/companies/posts/statistic-year")
    public ApiResponse<Map<Integer, Integer>> postStatisticByYearOfCompany(@RequestParam("year") int year, @RequestParam("companyId") int companyId){
        return ApiResponse.<Map<Integer, Integer>>builder().result(adminDashboardService.getPostCountsForYearOfCompany(year, companyId)).build();
    }
}