package com.attendance_service.dto;

import java.util.List;

public record FaceVerifyRequest(
        List<Double> embedding
) {}
