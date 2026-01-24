package com.payroll_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name="payroll_run")
public class PayrollRunEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;
    private Integer month;

    @Column(name="month_days")
    private Integer monthDays;

    @Column(name="created_by")
    private Long createdBy;

    public Long getId() { return id; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }
    public Integer getMonthDays() { return monthDays; }
    public void setMonthDays(Integer monthDays) { this.monthDays = monthDays; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
}
