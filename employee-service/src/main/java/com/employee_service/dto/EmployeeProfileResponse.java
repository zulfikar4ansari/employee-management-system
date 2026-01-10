package com.employee_service.dto;

/**
 * Immutable DTO returned to client.
 */
public record EmployeeProfileResponse(
        Long employeeId,
        String department,
        String role,
        String shift,
        Long payrollMappingId
) {}
