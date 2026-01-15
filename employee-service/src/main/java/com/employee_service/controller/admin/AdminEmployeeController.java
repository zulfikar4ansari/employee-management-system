package com.employee_service.controller.admin;

import com.common_lib.dto.ApiResponse;
import com.employee_service.dto.EmployeeProfileResponse;
import com.employee_service.dto.admin.EmployeeCreateRequest;
import com.employee_service.dto.admin.EmployeeUpdateRequest;
import com.employee_service.security.AdminGuard;
import com.employee_service.service.admin.EmployeeAdminService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final EmployeeAdminService adminService;
    private final AdminGuard adminGuard;

    public AdminEmployeeController(EmployeeAdminService adminService, AdminGuard adminGuard) {
        this.adminService = adminService;
        this.adminGuard = adminGuard;
    }

    @PostMapping
    public ApiResponse<EmployeeProfileResponse> create(
            @RequestHeader("X-Role") String role,
            @Valid @RequestBody EmployeeCreateRequest req
    ) {
        adminGuard.requireAdmin(role);
        return ApiResponse.ok(adminService.create(req), "Employee created");
    }

    @PutMapping("/{employeeId}")
    public ApiResponse<EmployeeProfileResponse> update(
            @RequestHeader("X-Role") String role,
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeUpdateRequest req
    ) {
        adminGuard.requireAdmin(role);
        return ApiResponse.ok(adminService.update(employeeId, req), "Employee updated");
    }

    @GetMapping("/{employeeId}")
    public ApiResponse<EmployeeProfileResponse> get(
            @RequestHeader("X-Role") String role,
            @PathVariable Long employeeId
    ) {
        adminGuard.requireAdmin(role);
        return ApiResponse.ok(adminService.get(employeeId));
    }

    @GetMapping
    public ApiResponse<List<EmployeeProfileResponse>> list(
            @RequestHeader("X-Role") String role
    ) {
        adminGuard.requireAdmin(role);
        return ApiResponse.ok(adminService.list(), "Employees list");
    }

    @DeleteMapping("/{employeeId}")
    public ApiResponse<String> delete(
            @RequestHeader("X-Role") String role,
            @PathVariable Long employeeId
    ) {
        adminGuard.requireAdmin(role);
        adminService.delete(employeeId);
        return ApiResponse.ok("Deleted", "Employee deleted");
    }
}
