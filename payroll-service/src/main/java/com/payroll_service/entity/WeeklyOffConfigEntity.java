package com.payroll_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name="weekly_off_config")
public class WeeklyOffConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sunday_off", nullable = false)
    private Boolean sundayOff;

    @Column(name="saturday_rule", nullable = false)
    private String saturdayRule; // ALL / 2ND_4TH / NONE

    public Long getId() { return id; }
    public Boolean getSundayOff() { return sundayOff; }
    public void setSundayOff(Boolean sundayOff) { this.sundayOff = sundayOff; }
    public String getSaturdayRule() { return saturdayRule; }
    public void setSaturdayRule(String saturdayRule) { this.saturdayRule = saturdayRule; }
}
