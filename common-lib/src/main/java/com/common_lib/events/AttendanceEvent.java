package com.ems.common.events;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Kafka event emitted by Attendance Service
 * and consumed by Payroll Service.
 *
 * Implemented as Java Record → immutable & thread-safe.
 */
public record AttendanceEvent(

        Long employeeId,          // employee identifier
        String eventType,         // IN / OUT
        LocalDate attendanceDay,  // business day
        OffsetDateTime timestamp, // event time
        Double workedHours        // only for OUT events
) {}
