package com.attendance_service.service;

import com.attendance_service.dto.AttendanceResponse;
import com.attendance_service.entity.AttendanceEntity;
import com.attendance_service.kafka.KafkaPublisher;
import com.attendance_service.repository.AttendanceRepository;

import com.common_lib.events.AttendanceNotificationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final QrValidationService qrValidationService;
    private final KafkaPublisher kafkaPublisher;

    @Value("${attendance.hr-mobile}")
    private String hrMobile;

    public AttendanceService(
            AttendanceRepository attendanceRepo,
            QrValidationService qrValidationService,
            KafkaPublisher kafkaPublisher
    ) {
        this.attendanceRepo = attendanceRepo;
        this.qrValidationService = qrValidationService;
        this.kafkaPublisher = kafkaPublisher;
    }

    public AttendanceResponse markAttendance(Long empId, String empMobile, String qrCodeValue) {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        var validation = qrValidationService.validate(qrCodeValue);

        String status = validation.valid() ? "PRESENT" : "ABSENT";
        String reason = validation.valid() ? null : validation.reason();

        AttendanceEntity entity = new AttendanceEntity();
        entity.setEmployeeId(empId);
        entity.setEmployeeMobile(empMobile);
        entity.setAttendanceDate(today);
        entity.setAttendanceTime(now);
        entity.setQrDate(validation.qrDate());
        entity.setQrCodeValue(qrCodeValue);
        entity.setStatus(status);
        entity.setReason(reason);

        attendanceRepo.save(entity);

        // Kafka Notification Event
        String msg = "Attendance " + status + " | EmpId=" + empId + " | Date=" + today + " | Time=" + now;

        AttendanceNotificationEvent event = new AttendanceNotificationEvent(
                empId,
                empMobile,
                hrMobile,
                status,
                qrCodeValue,
                today,
                now,
                msg
        );

        kafkaPublisher.publishAttendanceNotification(event);

        return new AttendanceResponse(empId, empMobile, qrCodeValue, status, reason, today, now);
    }
}
