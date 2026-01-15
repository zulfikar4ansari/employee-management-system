package com.employee_service.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeUpdateRequest(
        @NotBlank String department,
        @NotBlank String role,
        @NotBlank String shift,
        @NotNull Long payrollMappingId
) {}
