package com.payroll_service.controller;

import com.common_lib.dto.ApiResponse;
import com.payroll_service.dto.PayrollConfigRequest;
import com.payroll_service.dto.PayrollConfigResponse;
import com.payroll_service.entity.PayrollConfigEntity;
import com.payroll_service.repository.PayrollConfigRepository;
import com.payroll_service.security.AdminGuard;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/payroll/admin/config")
public class PayrollConfigController {

    private final PayrollConfigRepository repo;
    private final AdminGuard guard;

    public PayrollConfigController(PayrollConfigRepository repo, AdminGuard guard) {
        this.repo = repo;
        this.guard = guard;
    }

    @PostMapping
    public ApiResponse<String> upsert(
            @RequestHeader("X-Role") String role,
            @RequestBody PayrollConfigRequest req
    ) {
        guard.requireAdmin(role);

        PayrollConfigEntity e = new PayrollConfigEntity();
        e.setEmployeeId(req.employeeId());
        e.setGrossSalary(req.grossSalary());
        e.setBasic(req.basic());
        e.setHra(req.hra());
        e.setSpecialAllowance(req.specialAllowance());

        e.setPfEnabled(req.pfEnabled() != null ? req.pfEnabled() : true);
        e.setPfPercent(req.pfPercent() != null ? req.pfPercent() : 12.0);

        e.setEsiEnabled(req.esiEnabled() != null ? req.esiEnabled() : false);
        e.setEsiPercent(req.esiPercent() != null ? req.esiPercent() : 0.75);

        e.setPtAmount(req.ptAmount() != null ? req.ptAmount() : 200.0);
        e.setSalaryDaysMode(req.salaryDaysMode() == null ? "CALENDAR_DAYS" : req.salaryDaysMode());

        e.setCreatedAt(LocalDateTime.now());
        e.setUpdatedAt(LocalDateTime.now());

        repo.save(e);
        return ApiResponse.ok("OK", "Payroll config saved");
    }

    @GetMapping("/{employeeId}")
    public ApiResponse<PayrollConfigResponse> get(
            @RequestHeader("X-Role") String role,
            @PathVariable Long employeeId
    ) {
        guard.requireAdmin(role);

        PayrollConfigEntity e = repo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Payroll config not found for employee: " + employeeId));

        return ApiResponse.ok(new PayrollConfigResponse(
                e.getEmployeeId(),
                e.getGrossSalary(),
                e.getBasic(),
                e.getHra(),
                e.getSpecialAllowance(),
                e.getPfEnabled(),
                e.getPfPercent(),
                e.getEsiEnabled(),
                e.getEsiPercent(),
                e.getPtAmount(),
                e.getSalaryDaysMode()
        ));
    }
}
