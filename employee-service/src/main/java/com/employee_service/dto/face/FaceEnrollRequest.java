package com.employee_service.dto.face;

import jakarta.validation.constraints.NotBlank;

public record FaceEnrollRequest(
        @NotBlank String faceTemplate,       // embedding JSON string
        @NotBlank String templateVersion     // face-api.js@0.22 etc
) {}