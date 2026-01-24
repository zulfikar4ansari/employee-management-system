package com.payroll_service.repository;

public interface AttendanceStatsRepository {
    int countPresentDays(Long empId, java.time.LocalDate fromDate, java.time.LocalDate toDate);
}
