package com.payroll_service.security;

import org.springframework.stereotype.Component;

@Component
public class AdminGuard {

    public void requireAdmin(String roleHeader) {
        if (roleHeader == null || !roleHeader.equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Unauthorized: Admin role required");
        }
    }
}
