package com.employee_service.controller;


import com.common_lib.dto.ApiResponse;
import com.employee_service.dto.EmployeeMobileLookupResponse;
import com.employee_service.dto.EmployeeProfileResponse;
import com.employee_service.entity.EmployeeEntity;
import com.employee_service.service.EmployeeProfileService;
import org.springframework.web.bind.annotation.*;

/**
 * Gateway-ready controller.
 * No JWT parsing here.
 * Identity comes from API Gateway headers.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeProfileService service;

    public EmployeeController(EmployeeProfileService service) {
        this.service = service;
    }

    @GetMapping("/profile")
    public ApiResponse<EmployeeProfileResponse> profile(
            @RequestHeader("X-Employee-Id") Long employeeId,
            @RequestHeader(value = "X-Mobile", required = false) String mobile
    ) {

        EmployeeProfileResponse profile = service.getEmployeeProfileById(employeeId);

        return ApiResponse.ok(profile, "Employee profile fetched");
    }

    @GetMapping("/by-mobile/{mobile}")
    public ApiResponse<EmployeeMobileLookupResponse> getEmployeeByMobile(@PathVariable String mobile) {

        var emp = service.findByMobile(mobile);

        var resp = new EmployeeMobileLookupResponse(emp.getEmployeeId(), emp.getMobile());

        return ApiResponse.ok(resp, "Employee found");
    }
}
