package com.payroll_service.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class AttendanceStatsRepositoryImpl implements AttendanceStatsRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int countPresentDays(Long empId, LocalDate fromDate, LocalDate toDate) {

        String sql = """
            SELECT COUNT(*)
            FROM attendance_records
            WHERE employee_id = :empId
              AND attendance_date BETWEEN :fromDate AND :toDate
              AND status IN ('IN_MARKED','OUT_MARKED','PRESENT')
        """;

        Number result = (Number) em.createNativeQuery(sql)
                .setParameter("empId", empId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getSingleResult();

        return result.intValue();
    }
}
