package com.employee_service.dto.admin;


import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public record EmployeeCreateRequest(
        @NotNull Long employeeId,
        @NotBlank String mobile,
        @NotBlank String department,
        @NotBlank String role,   // ADMIN / EMPLOYEE
        @NotBlank String shift,
        @NotNull Long payrollMappingId
) {}
