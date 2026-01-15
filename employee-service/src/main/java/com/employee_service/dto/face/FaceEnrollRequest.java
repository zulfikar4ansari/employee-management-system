package com.employee_service.dto.face;

import jakarta.validation.constraints.NotBlank;

public record FaceEnrollRequest(
        @NotBlank String faceTemplate,    // embedding JSON string / base64
        @NotBlank String templateVersion  // e.g. face-api.js@0.22
) {}
