package com.ems.common.dto;

import java.time.OffsetDateTime;

/**
 * Standardized API error contract.
 */
public record ErrorResponse(

        String code,
        String message,
        OffsetDateTime timestamp
) {}
