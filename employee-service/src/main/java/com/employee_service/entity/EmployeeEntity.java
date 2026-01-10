package com.employee_service.entity;

import jakarta.persistence.*;

/**
 * JPA entity mapping for employees table.
 * Purpose: Connect Java object with MySQL record.
 */
@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String shift;

    @Column(name = "payroll_mapping_id", nullable = false)
    private Long payrollMappingId;

    // Getters and setters required by Hibernate proxy mechanism

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public Long getPayrollMappingId() { return payrollMappingId; }
    public void setPayrollMappingId(Long payrollMappingId) { this.payrollMappingId = payrollMappingId; }
}
