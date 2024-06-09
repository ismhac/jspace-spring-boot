package com.ismhac.jspace.service.dashboard;

import java.util.List;
import java.util.Map;

public interface AdminDashboardService {
    Map<Integer, Integer> getUserCountsForMonth(int year, int month);
    Map<Integer, Integer> getUserCountsForYear(int year);
    Map<Integer, Integer> getPostCountsForMonth(int year, int month);
    Map<Integer, Integer> getPostCountsForYear(int year);
    Map<Integer, Integer> getCompanyCountsForMonth(int year, int month);
    Map<Integer, Integer> getCompanyCountsForYear(int year);

    Map<Integer, Integer> getPostCountsForMonthOfCompany(int year, int month, int companyId);
    Map<Integer, Integer> getPostCountsForYearOfCompany(int year, int companyId);
}
