package com.ismhac.jspace.service.dashboard.impl;

import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.dashboard.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {
    private final UserRepository userRepository;

    public Map<Integer, Integer> getUserCountsForMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        List<Object[]> userCounts = userRepository.userStatisticByMonth(year, month, daysInMonth);

        Map<Integer, Integer> result = new HashMap<>();
        for (Object[] record : userCounts) {
            Integer day = ((Number) record[0]).intValue();
            Integer count = ((Number) record[1]).intValue();
            result.put(day, count);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            result.putIfAbsent(day, 0);
        }

        return result;
    }

    @Override
    public Map<Integer, Integer> getUserCountsForYear(int year) {
        List<Object[]> userCounts = userRepository.userStatisticByYear(year);

        Map<Integer, Integer> result = new HashMap<>();
        for (Object[] record : userCounts) {
            Integer month = ((Number) record[0]).intValue();
            Integer count = ((Number) record[1]).intValue();
            result.put(month, count);
        }

        for (int month = 1; month <= 12; month++) {
            result.putIfAbsent(month, 0);
        }

        return result;
    }

    @Override
    public Map<Integer, Integer> getPostCountsForMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        List<Object[]> userCounts = userRepository.postStatisticByMonth(year, month, daysInMonth);

        Map<Integer, Integer> result = new HashMap<>();
        for (Object[] record : userCounts) {
            Integer day = ((Number) record[0]).intValue();
            Integer count = ((Number) record[1]).intValue();
            result.put(day, count);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            result.putIfAbsent(day, 0);
        }

        return result;
    }

    @Override
    public Map<Integer, Integer> getPostCountsForYear(int year) {
        List<Object[]> userCounts = userRepository.postStatisticByYear(year);

        Map<Integer, Integer> result = new HashMap<>();
        for (Object[] record : userCounts) {
            Integer month = ((Number) record[0]).intValue();
            Integer count = ((Number) record[1]).intValue();
            result.put(month, count);
        }

        for (int month = 1; month <= 12; month++) {
            result.putIfAbsent(month, 0);
        }

        return result;
    }

    @Override
    public Map<Integer, Integer> getCompanyCountsForMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        List<Object[]> userCounts = userRepository.companyStatisticByMonth(year, month, daysInMonth);

        Map<Integer, Integer> result = new HashMap<>();
        for (Object[] record : userCounts) {
            Integer day = ((Number) record[0]).intValue();
            Integer count = ((Number) record[1]).intValue();
            result.put(day, count);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            result.putIfAbsent(day, 0);
        }

        return result;
    }

    @Override
    public Map<Integer, Integer> getCompanyCountsForYear(int year) {
        List<Object[]> userCounts = userRepository.companyStatisticByYear(year);

        Map<Integer, Integer> result = new HashMap<>();
        for (Object[] record : userCounts) {
            Integer month = ((Number) record[0]).intValue();
            Integer count = ((Number) record[1]).intValue();
            result.put(month, count);
        }

        for (int month = 1; month <= 12; month++) {
            result.putIfAbsent(month, 0);
        }

        return result;
    }

    @Override
    public Map<Integer, Integer> getPostCountsForMonthOfCompany(int year, int month, int companyId) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        List<Object[]> userCounts = userRepository.postStatisticByMonthOfCompany(year, month, daysInMonth, companyId);

        Map<Integer, Integer> result = new HashMap<>();
        for (Object[] record : userCounts) {
            Integer day = ((Number) record[0]).intValue();
            Integer count = ((Number) record[1]).intValue();
            result.put(day, count);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            result.putIfAbsent(day, 0);
        }

        return result;
    }

    @Override
    public Map<Integer, Integer> getPostCountsForYearOfCompany(int year, int companyId) {
        List<Object[]> userCounts = userRepository.postStatisticByYearOfCompany(year, companyId);

        Map<Integer, Integer> result = new HashMap<>();
        for (Object[] record : userCounts) {
            Integer month = ((Number) record[0]).intValue();
            Integer count = ((Number) record[1]).intValue();
            result.put(month, count);
        }

        for (int month = 1; month <= 12; month++) {
            result.putIfAbsent(month, 0);
        }

        return result;
    }
}
