package com.payroll_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll_config")
public class PayrollConfigEntity {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "gross_salary", nullable = false)
    private Double grossSalary;

    @Column(name = "basic", nullable = false)
    private Double basic;

    @Column(name = "hra", nullable = false)
    private Double hra;

    @Column(name = "special_allowance", nullable = false)
    private Double specialAllowance;

    @Column(name = "pf_enabled", nullable = false)
    private Boolean pfEnabled;

    @Column(name = "pf_percent", nullable = false)
    private Double pfPercent;

    @Column(name = "esi_enabled", nullable = false)
    private Boolean esiEnabled;

    @Column(name = "esi_percent", nullable = false)
    private Double esiPercent;

    @Column(name = "pt_amount", nullable = false)
    private Double ptAmount;

    @Column(name = "salary_days_mode", nullable = false)
    private String salaryDaysMode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // getters/setters
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Double getGrossSalary() { return grossSalary; }
    public void setGrossSalary(Double grossSalary) { this.grossSalary = grossSalary; }

    public Double getBasic() { return basic; }
    public void setBasic(Double basic) { this.basic = basic; }

    public Double getHra() { return hra; }
    public void setHra(Double hra) { this.hra = hra; }

    public Double getSpecialAllowance() { return specialAllowance; }
    public void setSpecialAllowance(Double specialAllowance) { this.specialAllowance = specialAllowance; }

    public Boolean getPfEnabled() { return pfEnabled; }
    public void setPfEnabled(Boolean pfEnabled) { this.pfEnabled = pfEnabled; }

    public Double getPfPercent() { return pfPercent; }
    public void setPfPercent(Double pfPercent) { this.pfPercent = pfPercent; }

    public Boolean getEsiEnabled() { return esiEnabled; }
    public void setEsiEnabled(Boolean esiEnabled) { this.esiEnabled = esiEnabled; }

    public Double getEsiPercent() { return esiPercent; }
    public void setEsiPercent(Double esiPercent) { this.esiPercent = esiPercent; }

    public Double getPtAmount() { return ptAmount; }
    public void setPtAmount(Double ptAmount) { this.ptAmount = ptAmount; }

    public String getSalaryDaysMode() { return salaryDaysMode; }
    public void setSalaryDaysMode(String salaryDaysMode) { this.salaryDaysMode = salaryDaysMode; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
