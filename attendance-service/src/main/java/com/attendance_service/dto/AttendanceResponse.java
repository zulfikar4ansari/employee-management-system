package com.attendance_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceResponse(
        Long employeeId,
        String employeeMobile,
        String qrCodeValue,
        String status,
        String reason,
        LocalDate attendanceDate,
        LocalTime attendanceTime
) {}
