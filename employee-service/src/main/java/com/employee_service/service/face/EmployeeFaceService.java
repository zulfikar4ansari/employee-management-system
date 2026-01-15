package com.employee_service.service.face;

import com.employee_service.dto.face.FaceEnrollRequest;
import com.employee_service.entity.EmployeeEntity;
import com.employee_service.entity.EmployeeFaceEntity;
import com.employee_service.repository.EmployeeFaceRepository;
import com.employee_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Service
public class EmployeeFaceService {

    private final EmployeeFaceRepository faceRepo;
    private final EmployeeRepository employeeRepo;

    public EmployeeFaceService(EmployeeFaceRepository faceRepo, EmployeeRepository employeeRepo) {
        this.faceRepo = faceRepo;
        this.employeeRepo = employeeRepo;
    }

    public void enroll(Long employeeId, FaceEnrollRequest req) {

        EmployeeEntity employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        String hash = sha256(req.faceTemplate());

        EmployeeFaceEntity entity = faceRepo.findByEmployeeId(employee.getEmployeeId())
                .orElseGet(EmployeeFaceEntity::new);

        boolean isNew = entity.getId() == null;

        entity.setEmployeeId(employee.getEmployeeId());
        entity.setFaceTemplate(req.faceTemplate());
        entity.setTemplateVersion(req.templateVersion());
        entity.setTemplateHash(hash);

        if (isNew) {
            entity.setCreatedAt(LocalDateTime.now());
        } else {
            entity.setUpdatedAt(LocalDateTime.now());
        }

        faceRepo.save(entity);
    }

    private String sha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hash generation failed", e);
        }
    }
}
