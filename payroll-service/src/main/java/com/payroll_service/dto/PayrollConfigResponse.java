package com.payroll_service.dto;

public record PayrollConfigResponse(
        Long employeeId,
        Double grossSalary,
        Double basic,
        Double hra,
        Double specialAllowance,
        Boolean pfEnabled,
        Double pfPercent,
        Boolean esiEnabled,
        Double esiPercent,
        Double ptAmount,
        String salaryDaysMode
) {}
