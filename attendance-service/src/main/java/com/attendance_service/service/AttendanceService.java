package com.attendance_service.service;

import com.attendance_service.dto.AttendanceResponse;
import com.attendance_service.entity.AttendanceEntity;
import com.attendance_service.kafka.KafkaPublisher;
import com.attendance_service.model.AttendanceActionResponse;
import com.attendance_service.repository.AttendanceRepository;

import com.common_lib.events.AttendanceNotificationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final QrValidationService qrValidationService;
    private final KafkaPublisher kafkaPublisher;

    private final FaceMatchService faceMatchService;

    @Value("${attendance.hr-mobile}")
    private String hrMobile;

    public AttendanceService(
            AttendanceRepository attendanceRepo,
            QrValidationService qrValidationService,
            KafkaPublisher kafkaPublisher,FaceMatchService faceMatchService
    ) {
        this.attendanceRepo = attendanceRepo;
        this.qrValidationService = qrValidationService;
        this.kafkaPublisher = kafkaPublisher;
        this.faceMatchService=faceMatchService;
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

    public AttendanceActionResponse markIn(Long employeeId, String employeeMobile) {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        AttendanceEntity entity = attendanceRepo.findByEmployeeIdAndAttendanceDate(employeeId, today)
                .orElseGet(() -> {
                    AttendanceEntity e = new AttendanceEntity();
                    e.setEmployeeId(employeeId);
                    e.setEmployeeMobile(employeeMobile);

                    // ✅ required NOT NULL fields
                    e.setAttendanceDate(today);
                    e.setAttendanceTime(now);

                    return e;
                });

        if (entity.getTimeIn() != null) {
            throw new RuntimeException("Time-IN already marked for today");
        }

        // ✅ IN time
        entity.setTimeIn(now);

        // ✅ keep attendanceTime updated (NOT NULL)
        entity.setAttendanceTime(now);

        entity.setStatus("IN_MARKED");

        attendanceRepo.save(entity);

        String msg = "Employee " + employeeId + " marked IN at " + now;

        AttendanceNotificationEvent event = new AttendanceNotificationEvent(
                employeeId,
                employeeMobile,
                hrMobile,   // from application.yml
                "IN",
                null,
                today,
                now,
                msg
        );

        kafkaPublisher.publishAttendanceNotification(event);

        return new AttendanceActionResponse(employeeId, "IN", today, now, entity.getStatus(), null);
    }


    public AttendanceActionResponse markOut(Long employeeId, String employeeMobile) {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        AttendanceEntity entity = attendanceRepo.findByEmployeeIdAndAttendanceDate(employeeId, today)
                .orElseThrow(() -> new RuntimeException("Time-IN not marked yet"));

        if (entity.getTimeOut() != null) {
            throw new RuntimeException("Time-OUT already marked for today");
        }

        entity.setTimeOut(now);

        int workedMinutes = 0;
        if (entity.getTimeIn() != null) {
            workedMinutes = (int) java.time.Duration.between(entity.getTimeIn(), now).toMinutes();
        }

        entity.setWorkedMinutes(workedMinutes);

        // ✅ keep attendanceTime updated (NOT NULL)
        entity.setAttendanceTime(now);

        entity.setStatus("OUT_MARKED");

        attendanceRepo.save(entity);

        String msg = "Employee " + employeeId + " marked OUT at " + now + " WorkedMinutes=" + workedMinutes;

        AttendanceNotificationEvent event = new AttendanceNotificationEvent(
                employeeId,
                employeeMobile,
                hrMobile,
                "OUT",
                null,
                today,
                now,
                msg
        );

        kafkaPublisher.publishAttendanceNotification(event);

        return new AttendanceActionResponse(employeeId, "OUT", today, now, entity.getStatus(), workedMinutes);
    }

    public AttendanceActionResponse markInWithFace(Long employeeId, String employeeMobile, List<Double> embedding) {

        boolean ok = faceMatchService.verify(employeeId, embedding);
        if (!ok) throw new RuntimeException("Face verification failed. Attendance denied.");

        return markIn(employeeId, employeeMobile); // reuse your existing logic
    }

    public AttendanceActionResponse markOutWithFace(Long employeeId, String employeeMobile, List<Double> embedding) {

        boolean ok = faceMatchService.verify(employeeId, embedding);
        if (!ok) throw new RuntimeException("Face verification failed. Attendance denied.");

        return markOut(employeeId, employeeMobile);
    }



}
