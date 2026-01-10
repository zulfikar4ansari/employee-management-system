package com.api_gateway.config;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controls which routes are open vs secured.
 */
@Component
public class SecurityRouteValidator {

    private static final List<String> publicEndpoints = List.of(
            "/auth/send-otp",
            "/auth/verify-otp",
            "/auth/"
    );

    public boolean isPublic(String path) {
        return publicEndpoints.stream().anyMatch(path::startsWith);
    }
}
