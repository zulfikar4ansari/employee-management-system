package com.attendance_service.controller;

import com.attendance_service.dto.AttendanceResponse;
import com.attendance_service.dto.QrScanRequest;
import com.attendance_service.model.AttendanceActionResponse;
import com.attendance_service.service.AttendanceService;
import com.common_lib.dto.ApiResponse;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @PostMapping("/scan-qr")
    public ApiResponse<AttendanceResponse> scanQr(
            @RequestHeader("X-Employee-Id") Long employeeId,
            @RequestHeader(value = "X-Mobile", required = false) String employeeMobile,
            @RequestBody QrScanRequest request
    ) {
        AttendanceResponse response = service.markAttendance(employeeId, employeeMobile, request.qrCodeValue());
        return ApiResponse.ok(response, "Attendance processed");
    }

    // New Added
    @PostMapping("/mark-in")
    public ApiResponse<AttendanceActionResponse> markIn(
            @RequestHeader("X-Employee-Id") Long employeeId,
            @RequestHeader("X-Mobile") String employeeMobile
    ) {
        AttendanceActionResponse res = service.markIn(employeeId, employeeMobile);
        return ApiResponse.ok(res, "IN marked successfully");
    }

    @PostMapping("/mark-out")
    public ApiResponse<AttendanceActionResponse> markOut(
            @RequestHeader("X-Employee-Id") Long employeeId,
            @RequestHeader("X-Mobile") String employeeMobile
    ) {
        AttendanceActionResponse res = service.markOut(employeeId, employeeMobile);
        return ApiResponse.ok(res, "OUT marked successfully");
    }


}
