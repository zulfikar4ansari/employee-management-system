package com.attendance_service.kafka;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Kafka message payload.
 * This will be consumed by Notification-Service.
 */
public record AttendanceNotificationEvent(
        Long employeeId,
        String employeeMobile,
        String hrMobile,
        String status,        // PRESENT/ABSENT
        String qrCodeValue,
        LocalDate date,
        LocalTime time,
        String message
) {}
