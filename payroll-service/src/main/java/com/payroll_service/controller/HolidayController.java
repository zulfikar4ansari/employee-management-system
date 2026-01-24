package com.payroll_service.controller;

import com.common_lib.dto.ApiResponse;
import com.payroll_service.dto.HolidayRequest;
import com.payroll_service.dto.HolidayResponse;
import com.payroll_service.entity.HolidayEntity;
import com.payroll_service.repository.HolidayRepository;
import com.payroll_service.security.AdminGuard;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payroll/admin/holidays")
public class HolidayController {

    private final HolidayRepository repo;
    private final AdminGuard guard;

    public HolidayController(HolidayRepository repo, AdminGuard guard) {
        this.repo = repo;
        this.guard = guard;
    }

    @PostMapping
    public ApiResponse<HolidayResponse> create(
            @RequestHeader("X-Role") String role,
            @RequestBody HolidayRequest req
    ) {
        guard.requireAdmin(role);

        HolidayEntity h = new HolidayEntity();
        h.setHolidayDate(req.holidayDate());
        h.setHolidayName(req.holidayName());
        h.setPaid(req.paid() != null ? req.paid() : true);

        HolidayEntity saved = repo.save(h);

        return ApiResponse.ok(new HolidayResponse(
                saved.getId(), saved.getHolidayDate(), saved.getHolidayName(), saved.getPaid()
        ), "Holiday saved");
    }

    @GetMapping
    public ApiResponse<List<HolidayResponse>> list(@RequestHeader("X-Role") String role) {
        guard.requireAdmin(role);

        List<HolidayResponse> list = repo.findAll().stream()
                .map(h -> new HolidayResponse(h.getId(), h.getHolidayDate(), h.getHolidayName(), h.getPaid()))
                .toList();

        return ApiResponse.ok(list);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(
            @RequestHeader("X-Role") String role,
            @PathVariable Long id
    ) {
        guard.requireAdmin(role);
        repo.deleteById(id);
        return ApiResponse.ok("OK", "Holiday deleted");
    }
}
