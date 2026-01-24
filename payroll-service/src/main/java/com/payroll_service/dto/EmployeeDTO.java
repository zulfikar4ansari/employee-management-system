package com.payroll_service.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String department;
    private Double salary;
    private Boolean active;
}
