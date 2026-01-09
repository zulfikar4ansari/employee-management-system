package com.auth.controller;

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

    private final OtpService otpService;
    private final JwtTokenService jwtTokenService;
    private final AuthSessionService sessionService;

    public AuthController(
            OtpService otpService,
            JwtTokenService jwtTokenService,
            AuthSessionService sessionService) {
        this.otpService = otpService;
        this.jwtTokenService = jwtTokenService;
        this.sessionService = sessionService;
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

        Long employeeId = 1001L; // Will be DB-driven later

        String accessToken =
                jwtTokenService.generateAccessToken(req.mobile(), employeeId);

        String refreshToken =
                jwtTokenService.generateRefreshToken();

        // Store login session in Redis
        sessionService.storeSession(
                jwtTokenService.createSession(req.mobile(), employeeId)
        );

        return ApiResponse.ok(
                new AuthResponse(accessToken, refreshToken),
                "Login successful"
        );
    }
}