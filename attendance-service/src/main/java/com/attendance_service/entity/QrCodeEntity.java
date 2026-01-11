package com.attendance_service.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "qt_table")
public class QrCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qr_date", nullable = false)
    private LocalDate qrDate;

    @Column(name = "qr_code_value", nullable = false, length = 16)
    private String qrCodeValue;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    public Long getId() { return id; }

    public LocalDate getQrDate() { return qrDate; }
    public void setQrDate(LocalDate qrDate) { this.qrDate = qrDate; }

    public String getQrCodeValue() { return qrCodeValue; }
    public void setQrCodeValue(String qrCodeValue) { this.qrCodeValue = qrCodeValue; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
