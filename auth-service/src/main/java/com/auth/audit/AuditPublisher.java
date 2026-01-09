package com.auth.audit;

import org.springframework.stereotype.Component;

/**
 * Placeholder for audit logging.
 * Later this will publish to Audit Service.
 */
@Component
public class AuditPublisher
{
    public void log(String event) {
        System.out.println("AUDIT LOG :: " + event);
    }
}
