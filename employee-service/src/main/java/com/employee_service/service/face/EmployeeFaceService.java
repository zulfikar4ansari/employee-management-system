package com.employee_service.service.face;

import com.employee_service.dto.face.FaceEnrollRequest;
import com.employee_service.dto.face.FaceResponse;
import com.employee_service.entity.EmployeeFaceEntity;
import com.employee_service.repository.EmployeeFaceRepository;
import com.employee_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public FaceResponse enrollOrUpdate(Long employeeId, FaceEnrollRequest req) {

        // validate employee exists
        employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        String hash = sha256(req.faceTemplate());

        EmployeeFaceEntity entity = faceRepo.findByEmployeeId(employeeId)
                .orElseGet(EmployeeFaceEntity::new);

        boolean isNew = entity.getId() == null;

        entity.setEmployeeId(employeeId);
        entity.setFaceTemplate(req.faceTemplate());
        entity.setTemplateVersion(req.templateVersion());
        entity.setTemplateHash(hash);

        if (isNew) {
            entity.setCreatedAt(LocalDateTime.now());
        } else {
            entity.setUpdatedAt(LocalDateTime.now());
        }

        EmployeeFaceEntity saved = faceRepo.save(entity);

        return new FaceResponse(
                saved.getEmployeeId(),
                saved.getTemplateVersion(),
                saved.getTemplateHash(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    public FaceResponse get(Long employeeId) {
        EmployeeFaceEntity e = faceRepo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Face template not found for employeeId=" + employeeId));

        return new FaceResponse(
                e.getEmployeeId(),
                e.getTemplateVersion(),
                e.getTemplateHash(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

    @Transactional
    public void delete(Long employeeId) {
        faceRepo.deleteByEmployeeId(employeeId);
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
