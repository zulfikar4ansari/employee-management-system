package com.payroll_service.dto;

public record PayrollSlipResponse(
        Long employeeId,
        Integer presentDays,
        Integer weeklyOffDays,
        Integer holidayDays,
        Integer absentDays,
        Double perDaySalary,
        Double absentDeduction,
        Double payableGross,
        Double pfAmount,
        Double ptAmount,
        Double netSalary
) {}
