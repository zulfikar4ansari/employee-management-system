package com.auth.dto;

public record EmployeeMobileLookupResponse(
        Long employeeId,
        String mobile,
        String role
) {}