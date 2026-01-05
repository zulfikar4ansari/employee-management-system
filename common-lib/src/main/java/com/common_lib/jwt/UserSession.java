package com.ems.common.jwt;

import java.time.OffsetDateTime;

/**
 * Represents authenticated user session data stored in Redis
 * and embedded inside JWT claims.
 */
public record UserSession(

        String mobile,
        Long employeeId,
        OffsetDateTime issuedAt
) {}
