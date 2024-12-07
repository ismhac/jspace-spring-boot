package com.ismhac.jspace.service.dashboard.impl;

import com.ismhac.jspace.repository.UserRepository;
import com.ismhac.jspace.service.dashboard.AdminDashboardService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {
    private final UserRepository userRepository;
    private final EntityManager entityManager;

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
    public Object adminDashboardStatistic() {
        Map<String, Object> result = (Map<String, Object>) entityManager.createNativeQuery("""
                                        with countUser as(
                                            select coalesce(count(*), 0) as user_number
                                            from tbl_user
                                        ),
                                        countCompany as (
                                            select coalesce(count(*), 0) as company_number
                                            from tbl_company
                                        ),
                                        countProduct as (
                                            select coalesce(count(*), 0) as product_number
                                            from tbl_product
                                        ),
                                        countPost as (
                                            select coalesce(count(*), 0) as post_number
                                            from tbl_post
                                        ),
                                        countRevenue as (
                                            select cast(COALESCE(SUM(tph.total_price), 0) AS DOUBLE PRECISION) AS total_revenue
                                            from tbl_purchase_history tph
                                        )
                                        select
                                            countUser.user_number,
                                            countCompany.company_number,
                                            countProduct.product_number,
                                            countPost.post_number,
                                            countRevenue.total_revenue
                                        from countUser, countCompany, countProduct, countPost, countRevenue
                                """,
                        Map.class)
                .getSingleResult();

        NumberFormat formatter = new DecimalFormat("#0");
        result.put("total_revenue", BigDecimal.valueOf(Long.parseLong(formatter.format(result.get("total_revenue")))));
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

    @Override
    public Object companyStatistic(int companyId) {
        Map<String, Object> result = (Map<String, Object>) entityManager.createNativeQuery("""
                                        with countUser as(
                                            select coalesce(count(distinct tcp.candidate_id), 0) candidate_number
                                            from tbl_candidate_post tcp
                                            join tbl_post tp on tcp.post_id = tp.id
                                            where tp.company_id = :companyId
                                        ),
                                        countProduct as (
                                             select coalesce(count(distinct tpp.product_name), 0) as product_number
                                             from tbl_purchased_product tpp
                                             where tpp.company_id = :companyId
                                        ),
                                        countPost as (
                                             select coalesce(count(*), 0) as post_number
                                             from tbl_post tp
                                             where tp.company_id = :companyId
                                        ),
                                         countCost as (
                                             select coalesce(sum(tph.total_price), 0) as total_cost
                                             from tbl_purchase_history tph
                                             where tph.company_id = :companyId
                                         )
                                        select
                                            countUser.candidate_number,
                                            countProduct.product_number,
                                            countPost.post_number,
                                            countCost.total_cost
                                        from countUser, countProduct, countPost, countCost
                                """,
                        Map.class)
                .setParameter("companyId", companyId)
                .getSingleResult();

        NumberFormat formatter = new DecimalFormat("#0");
        result.put("total_cost", BigDecimal.valueOf(Long.parseLong(formatter.format(result.get("total_cost")))));
        return result;
    }
}
