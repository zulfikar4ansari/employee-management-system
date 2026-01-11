package com.attendance_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="attendance_records")
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="employee_id", nullable=false)
    private Long employeeId;

    @Column(name="employee_mobile")
    private String employeeMobile;

    @Column(name="attendance_date", nullable=false)
    private LocalDate attendanceDate;

    @Column(name="attendance_time", nullable=false)
    private LocalTime attendanceTime;

    @Column(name="qr_date", nullable=false)
    private LocalDate qrDate;

    @Column(name="qr_code_value", nullable=false, length=16)
    private String qrCodeValue;

    @Column(name="status", nullable=false)
    private String status;

    @Column(name="reason")
    private String reason;

    public Long getId() { return id; }
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public String getEmployeeMobile() { return employeeMobile; }
    public void setEmployeeMobile(String employeeMobile) { this.employeeMobile = employeeMobile; }
    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }
    public LocalTime getAttendanceTime() { return attendanceTime; }
    public void setAttendanceTime(LocalTime attendanceTime) { this.attendanceTime = attendanceTime; }
    public LocalDate getQrDate() { return qrDate; }
    public void setQrDate(LocalDate qrDate) { this.qrDate = qrDate; }
    public String getQrCodeValue() { return qrCodeValue; }
    public void setQrCodeValue(String qrCodeValue) { this.qrCodeValue = qrCodeValue; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
