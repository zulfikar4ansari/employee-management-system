package com.employee_service.security;

import com.common_lib.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class AdminGuard {

    public void requireAdmin(String role) {
        if (role == null || !"ADMIN".equalsIgnoreCase(role)) {
            throw new BusinessException("FORBIDDEN", "Admin access required");
        }
    }
}
