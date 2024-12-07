package com.ismhac.jspace.service.dashboard;

import java.util.Map;

public interface AdminDashboardService {

    /* ADMIN */
    Map<Integer, Integer> getUserCountsForMonth(int year, int month);
    Map<Integer, Integer> getUserCountsForYear(int year);
    Map<Integer, Integer> getPostCountsForMonth(int year, int month);
    Map<Integer, Integer> getPostCountsForYear(int year);
    Map<Integer, Integer> getCompanyCountsForMonth(int year, int month);
    Map<Integer, Integer> getCompanyCountsForYear(int year);

    //
    Object adminDashboardStatistic();


    /* EMPLOYEE */
    Map<Integer, Integer> getPostCountsForMonthOfCompany(int year, int month, int companyId);
    Map<Integer, Integer> getPostCountsForYearOfCompany(int year, int companyId);

    Object companyStatistic(int companyId);
}
