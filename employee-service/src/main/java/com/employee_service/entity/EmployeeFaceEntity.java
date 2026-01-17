package com.employee_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_face")
public class EmployeeFaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="employee_id", nullable = false, unique = true)
    private Long employeeId;

    @Lob
    @Column(name="face_template", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String faceTemplate;

    @Column(name="template_version", nullable = false)
    private String templateVersion;

    @Column(name="template_hash", nullable = false, length = 128)
    private String templateHash;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public Long getId() { return id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getFaceTemplate() { return faceTemplate; }
    public void setFaceTemplate(String faceTemplate) { this.faceTemplate = faceTemplate; }

    public String getTemplateVersion() { return templateVersion; }
    public void setTemplateVersion(String templateVersion) { this.templateVersion = templateVersion; }

    public String getTemplateHash() { return templateHash; }
    public void setTemplateHash(String templateHash) { this.templateHash = templateHash; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
