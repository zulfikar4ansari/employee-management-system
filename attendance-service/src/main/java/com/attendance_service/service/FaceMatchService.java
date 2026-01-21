package com.attendance_service.service;

import com.attendance_service.entity.EmployeeFaceEntity;
import com.attendance_service.repository.EmployeeFaceRepository;
import com.common_lib.utils.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FaceMatchService {

    private final EmployeeFaceRepository repo;

    // Face-api.js typically works best around 0.45–0.6 threshold depending.
    private static final double COSINE_THRESHOLD = 0.55;

    public FaceMatchService(EmployeeFaceRepository repo) {
        this.repo = repo;
    }

    public boolean verify(Long employeeId, List<Double> incomingEmbedding) {

        EmployeeFaceEntity face = repo.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Face template not enrolled for employee: " + employeeId));

        // Template JSON stored like:
        // { "embedding":[...], "dim":128 }
        Map template = JsonUtils.fromJson(face.getFaceTemplate(), Map.class);

        List<Double> stored = (List<Double>) template.get("embedding");

        if (stored == null || stored.size() != incomingEmbedding.size()) {
            throw new RuntimeException("Invalid stored face template");
        }

        double similarity = cosineSimilarity(stored, incomingEmbedding);

        return similarity >= COSINE_THRESHOLD;
    }

    private double cosineSimilarity(List<Double> a, List<Double> b) {
        double dot = 0.0, normA = 0.0, normB = 0.0;

        for (int i = 0; i < a.size(); i++) {
            double x = a.get(i);
            double y = b.get(i);
            dot += x * y;
            normA += x * x;
            normB += y * y;
        }

        if (normA == 0 || normB == 0) return 0;

        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }


}
