package com.notification_service.service;


import com.common_lib.events.AttendanceNotificationEvent;
import org.springframework.stereotype.Service;

/**
 * Builds human readable messages.
 */
@Service
public class TemplateService {

    public String buildEmployeeMessage(AttendanceNotificationEvent e) {
        return "Your attendance is marked as " + e.status()
                + " on " + e.date() + " at " + e.time();
    }

    public String buildHrMessage(AttendanceNotificationEvent e) {
        return "HR Alert: EmpId=" + e.employeeId()
                + " Attendance=" + e.status()
                + " Date=" + e.date() + " Time=" + e.time();
    }
}
