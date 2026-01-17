package com.employee_service.controller.admin;

import com.common_lib.dto.ApiResponse;
import com.employee_service.dto.face.FaceEnrollRequest;
import com.employee_service.dto.face.FaceResponse;
import com.employee_service.security.AdminGuard;
import com.employee_service.service.face.EmployeeFaceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employees/{employeeId}/face")
public class AdminFaceController {

    private final EmployeeFaceService faceService;
    private final AdminGuard adminGuard;

    public AdminFaceController(EmployeeFaceService faceService, AdminGuard adminGuard) {
        this.faceService = faceService;
        this.adminGuard = adminGuard;
    }

    @PostMapping
    public ApiResponse<FaceResponse> enrollOrUpdate(
            @RequestHeader("X-Role") String role,
            @PathVariable Long employeeId,
            @Valid @RequestBody FaceEnrollRequest req
    ) {
        adminGuard.requireAdmin(role);
        return ApiResponse.ok(faceService.enrollOrUpdate(employeeId, req), "Face template saved");
    }

    @GetMapping
    public ApiResponse<FaceResponse> get(
            @RequestHeader("X-Role") String role,
            @PathVariable Long employeeId
    ) {
        adminGuard.requireAdmin(role);
        return ApiResponse.ok(faceService.get(employeeId), "Face template fetched");
    }

    @DeleteMapping
    public ApiResponse<String> delete(
            @RequestHeader("X-Role") String role,
            @PathVariable Long employeeId
    ) {
        adminGuard.requireAdmin(role);
        faceService.delete(employeeId);
        return ApiResponse.ok("Deleted", "Face template removed");
    }
}
