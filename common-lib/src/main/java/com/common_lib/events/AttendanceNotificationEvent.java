package com.common_lib.events;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Shared Kafka event between Attendance-Service and Notification-Service.
 */
public record AttendanceNotificationEvent(
        Long employeeId,
        String employeeMobile,
        String hrMobile,
        String status,
        String qrCodeValue,
        LocalDate date,
        LocalTime time,
        String message
) {}
