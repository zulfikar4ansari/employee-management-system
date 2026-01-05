package com.auth.model;

/**
 * Request payload for OTP verification.
 */
public record OtpVerifyRequest(
        String mobile,
        String otp
) {}
