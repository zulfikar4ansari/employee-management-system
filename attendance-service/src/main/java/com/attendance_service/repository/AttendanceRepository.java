package com.attendance_service.repository;

import com.attendance_service.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    Optional<AttendanceEntity> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);

}
