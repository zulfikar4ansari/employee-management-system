package com.payroll_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name="payroll_slip")
public class PayrollSlipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="payroll_run_id", nullable=false)
    private Long payrollRunId;

    @Column(name="employee_id", nullable=false)
    private Long employeeId;

    private Integer presentDays;
    private Integer weeklyOffDays;
    private Integer holidayDays;
    private Integer absentDays;

    private Double perDaySalary;
    private Double absentDeduction;
    private Double payableGross;

    private Double pfAmount;
    private Double esiAmount;
    private Double ptAmount;

    private Double netSalary;

    // getters/setters
    public Long getId() { return id; }

    public Long getPayrollRunId() { return payrollRunId; }
    public void setPayrollRunId(Long payrollRunId) { this.payrollRunId = payrollRunId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Integer getPresentDays() { return presentDays; }
    public void setPresentDays(Integer presentDays) { this.presentDays = presentDays; }

    public Integer getWeeklyOffDays() { return weeklyOffDays; }
    public void setWeeklyOffDays(Integer weeklyOffDays) { this.weeklyOffDays = weeklyOffDays; }

    public Integer getHolidayDays() { return holidayDays; }
    public void setHolidayDays(Integer holidayDays) { this.holidayDays = holidayDays; }

    public Integer getAbsentDays() { return absentDays; }
    public void setAbsentDays(Integer absentDays) { this.absentDays = absentDays; }

    public Double getPerDaySalary() { return perDaySalary; }
    public void setPerDaySalary(Double perDaySalary) { this.perDaySalary = perDaySalary; }

    public Double getAbsentDeduction() { return absentDeduction; }
    public void setAbsentDeduction(Double absentDeduction) { this.absentDeduction = absentDeduction; }

    public Double getPayableGross() { return payableGross; }
    public void setPayableGross(Double payableGross) { this.payableGross = payableGross; }

    public Double getPfAmount() { return pfAmount; }
    public void setPfAmount(Double pfAmount) { this.pfAmount = pfAmount; }

    public Double getEsiAmount() { return esiAmount; }
    public void setEsiAmount(Double esiAmount) { this.esiAmount = esiAmount; }

    public Double getPtAmount() { return ptAmount; }
    public void setPtAmount(Double ptAmount) { this.ptAmount = ptAmount; }

    public Double getNetSalary() { return netSalary; }
    public void setNetSalary(Double netSalary) { this.netSalary = netSalary; }
}
