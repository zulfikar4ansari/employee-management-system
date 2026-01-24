package com.payroll_service.controller;

import com.common_lib.dto.ApiResponse;
import com.payroll_service.dto.PayrollRunRequest;
import com.payroll_service.dto.PayrollRunResponse;
import com.payroll_service.security.AdminGuard;
import com.payroll_service.service.PayrollRunService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payroll/admin/run")
public class PayrollRunController {

    private final PayrollRunService service;
    private final AdminGuard guard;

    public PayrollRunController(PayrollRunService service, AdminGuard guard) {
        this.service = service;
        this.guard = guard;
    }

    @PostMapping
    public ApiResponse<PayrollRunResponse> generate(
            @RequestHeader("X-Role") String role,
            @RequestHeader("X-Employee-Id") Long adminId,
            @RequestBody PayrollRunRequest req
    ) {
        guard.requireAdmin(role);

        PayrollRunResponse res = service.generatePayroll(req.year(), req.month(), adminId);
        return ApiResponse.ok(res, "Payroll generated successfully");
    }
}
