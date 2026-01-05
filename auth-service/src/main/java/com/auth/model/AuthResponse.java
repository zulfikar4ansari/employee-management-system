package com.auth.model;

/**
 * Response returned after successful authentication.
 */
public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
