package com.attendance_service.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceActionResponse(
        Long employeeId,
        String eventType,
        LocalDate attendanceDate,
        LocalTime attendanceTime,
        String status,
        Integer workedMinutes
) {}
