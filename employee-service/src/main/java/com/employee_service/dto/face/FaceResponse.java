package com.employee_service.dto.face;

import java.time.LocalDateTime;

public record FaceResponse(
        Long employeeId,
        String templateVersion,
        String templateHash,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
