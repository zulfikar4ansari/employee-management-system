package com.auth.controller;

import com.auth.client.EmployeeClient;
import com.auth.model.AuthResponse;
import com.auth.model.OtpRequest;
import com.auth.model.OtpVerifyRequest;
import com.auth.service.AuthSessionService;
import com.auth.service.JwtTokenService;
import com.auth.service.OtpService;
import com.common_lib.dto.ApiResponse;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes OTP and authentication APIs.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final EmployeeClient employeeClient;
    private final OtpService otpService;
    private final JwtTokenService jwtTokenService;
    private final AuthSessionService sessionService;

    public AuthController(
            OtpService otpService,
            JwtTokenService jwtTokenService,
            AuthSessionService sessionService,EmployeeClient employeeClient) {
        this.otpService = otpService;
        this.jwtTokenService = jwtTokenService;
        this.sessionService = sessionService;
        this.employeeClient=employeeClient;
    }

    /**
     * STEP-1: Generate OTP
     */
    @PostMapping("/send-otp")
    public ApiResponse<String> sendOtp(@RequestBody OtpRequest req) {

        otpService.generateOtp(req.mobile());
        return ApiResponse.ok("OTP sent to WhatsApp");
    }

    /**
     * STEP-2: Verify OTP → Issue JWT → Store Session
     */

    @PostMapping("/verify-otp")
    public ApiResponse<AuthResponse> verifyOtp(@RequestBody OtpVerifyRequest req) {

        if (!otpService.validateOtp(req.mobile(), req.otp())) {
            throw new RuntimeException("Invalid OTP");
        }

        // ✅ Fetch employee info
        var emp = employeeClient.getEmployeeByMobile(req.mobile());

        String access = jwtTokenService.generateAccessToken(req.mobile(), emp.employeeId(), emp.role());
        String refresh = jwtTokenService.generateRefreshToken();

        sessionService.storeSession(jwtTokenService.createSession(req.mobile(), emp.employeeId()));

        return ApiResponse.ok(new AuthResponse(access, refresh), "Login successful");
    }

}