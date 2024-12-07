package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.enums.RoleCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    @Query("""
            select t1 from User t1 where t1.username = :username or t1.email = :email
            """)
    Optional<User> findUserByUsernameOrEmail(String username, String email);

    @Query("""
            select t1  from User t1 where (:roleId is null or t1.role.id = :roleId)
                and (t1.role.id != 1)
                and (:name is null or :name = '' or  lower(t1.name) like lower(concat('%', :name, '%')))
                and (:email is null or :email = '' or lower(t1.email) like lower(concat('%', :email, '%')))
                and (:activated is null or t1.activated = :activated)
            """)
    Page<User> superAdminGetPageUserAndFilterByNameAndEmailAndActivated(@Param("roleId") Integer roleId, @Param("name") String name, @Param("email") String email, @Param("activated") Boolean activated, Pageable pageable);

    @Query("""
            select t1  from User t1 where (:roleId is null or t1.role.id = :roleId)
                and (t1.role.id != 1 and t1.role.id != 2)
                and (:name is null or :name = '' or  lower(t1.name) like lower(concat('%', :name, '%')))
                and (:email is null or :email = '' or lower(t1.email) like lower(concat('%', :email, '%')))
                and (:activated is null or t1.activated = :activated)
            """)
    Page<User> adminGetPageUserAndFilterByNameAndEmailAndActivated(@Param("roleId") Integer roleId, @Param("name") String name, @Param("email") String email, @Param("activated") Boolean activated, Pageable pageable);

    @Query(value = """
            WITH RECURSIVE days AS (
                SELECT 1 AS day
                UNION ALL
                SELECT day + 1
                FROM days
                WHERE day < :daysInMonth
            )
            SELECT days.day, COALESCE(user_counts.user_count, 0) AS user_count
            FROM days
            LEFT JOIN (
                SELECT EXTRACT(DAY FROM tu.created_at) AS day, COUNT(tu.id) AS user_count
                FROM tbl_user tu
                WHERE EXTRACT(YEAR FROM tu.created_at) = :year AND EXTRACT(MONTH FROM tu.created_at) = :month
                GROUP BY day
            ) AS user_counts ON days.day = user_counts.day
            ORDER BY days.day
            """, nativeQuery = true)
    List<Object[]> userStatisticByMonth(@Param("year") int year, @Param("month") int month, @Param("daysInMonth") int daysInMonth);

    @Query(value = """
            WITH RECURSIVE months AS (
                SELECT 1 AS month
                UNION ALL
                SELECT month + 1
                FROM months
                WHERE month < 12
            )
            SELECT months.month, COALESCE(user_counts.user_count, 0) AS user_count
            FROM months
            LEFT JOIN (
                SELECT EXTRACT(MONTH FROM tu.created_at) AS month, COUNT(tu.id) AS user_count
                FROM tbl_user tu
                WHERE EXTRACT(YEAR FROM tu.created_at) = :year
                GROUP BY month
            ) AS user_counts ON months.month = user_counts.month
            ORDER BY months.month
            """, nativeQuery = true)
    List<Object[]> userStatisticByYear(@Param("year") int year);

    @Query(value = """
            WITH RECURSIVE days AS (
                SELECT 1 AS day
                UNION ALL
                SELECT day + 1
                FROM days
                WHERE day < :daysInMonth
            )
            SELECT days.day, COALESCE(user_counts.user_count, 0) AS user_count
            FROM days
            LEFT JOIN (
                SELECT EXTRACT(DAY FROM tp.created_at) AS day, COUNT(tp.id) AS user_count
                FROM tbl_post tp
                WHERE EXTRACT(YEAR FROM tp.created_at) = :year AND EXTRACT(MONTH FROM tp.created_at) = :month
                GROUP BY day
            ) AS user_counts ON days.day = user_counts.day
            ORDER BY days.day
            """, nativeQuery = true)
    List<Object[]> postStatisticByMonth(@Param("year") int year, @Param("month") int month, @Param("daysInMonth") int daysInMonth);

    @Query(value = """
            WITH RECURSIVE months AS (
                SELECT 1 AS month
                UNION ALL
                SELECT month + 1
                FROM months
                WHERE month < 12
            )
            SELECT months.month, COALESCE(user_counts.user_count, 0) AS user_count
            FROM months
            LEFT JOIN (
                SELECT EXTRACT(MONTH FROM tp.created_at) AS month, COUNT(tp.id) AS user_count
                FROM tbl_post tp
                WHERE EXTRACT(YEAR FROM tp.created_at) = :year
                GROUP BY month
            ) AS user_counts ON months.month = user_counts.month
            ORDER BY months.month
            """, nativeQuery = true)
    List<Object[]> postStatisticByYear(@Param("year") int year);

    @Query(value = """
            WITH RECURSIVE days AS (
                SELECT 1 AS day
                UNION ALL
                SELECT day + 1
                FROM days
                WHERE day < :daysInMonth
            )
            SELECT days.day, COALESCE(user_counts.user_count, 0) AS user_count
            FROM days
            LEFT JOIN (
                SELECT EXTRACT(DAY FROM tc.created_at) AS day, COUNT(tc.id) AS user_count
                FROM tbl_company tc
                WHERE EXTRACT(YEAR FROM tc.created_at) = :year AND EXTRACT(MONTH FROM tc.created_at) = :month
                GROUP BY day
            ) AS user_counts ON days.day = user_counts.day
            ORDER BY days.day
            """, nativeQuery = true)
    List<Object[]> companyStatisticByMonth(@Param("year") int year, @Param("month") int month, @Param("daysInMonth") int daysInMonth);

    @Query(value = """
            WITH RECURSIVE months AS (
                SELECT 1 AS month
                UNION ALL
                SELECT month + 1
                FROM months
                WHERE month < 12
            )
            SELECT months.month, COALESCE(user_counts.user_count, 0) AS user_count
            FROM months
            LEFT JOIN (
                SELECT EXTRACT(MONTH FROM tc.created_at) AS month, COUNT(tc.id) AS user_count
                FROM tbl_company tc
                WHERE EXTRACT(YEAR FROM tc.created_at) = :year
                GROUP BY month
            ) AS user_counts ON months.month = user_counts.month
            ORDER BY months.month
            """, nativeQuery = true)
    List<Object[]> companyStatisticByYear(@Param("year") int year);

    @Query(value = """
            WITH RECURSIVE days AS (
                SELECT 1 AS day
                UNION ALL
                SELECT day + 1
                FROM days
                WHERE day < :daysInMonth
            )
            SELECT days.day, COALESCE(user_counts.user_count, 0) AS user_count
            FROM days
            LEFT JOIN (
                SELECT EXTRACT(DAY FROM tp.created_at) AS day, COUNT(tp.id) AS user_count
                FROM tbl_post tp
                WHERE EXTRACT(YEAR FROM tp.created_at) = :year AND EXTRACT(MONTH FROM tp.created_at) = :month AND tp.company_id = :companyId
                GROUP BY day
            ) AS user_counts ON days.day = user_counts.day
            ORDER BY days.day
            """, nativeQuery = true)
    List<Object[]> postStatisticByMonthOfCompany(@Param("year") int year, @Param("month") int month, @Param("daysInMonth") int daysInMonth, @Param("companyId") int companyId);

    @Query(value = """
            WITH RECURSIVE months AS (
                SELECT 1 AS month
                UNION ALL
                SELECT month + 1
                FROM months
                WHERE month < 12
            )
            SELECT months.month, COALESCE(user_counts.user_count, 0) AS user_count
            FROM months
            LEFT JOIN (
                SELECT EXTRACT(MONTH FROM tp.created_at) AS month, COUNT(tp.id) AS user_count
                FROM tbl_post tp
                WHERE EXTRACT(YEAR FROM tp.created_at) = :year AND tp.company_id = :companyId
                GROUP BY month
            ) AS user_counts ON months.month = user_counts.month
            ORDER BY months.month
            """, nativeQuery = true)
    List<Object[]> postStatisticByYearOfCompany(@Param("year") int year, @Param("companyId") int companyId);

    @Query("""
            select u
            from User u where u.role.code in :roleCodes
            """)
    List<User> findUserInListRole(List<RoleCode> roleCodes);
}
