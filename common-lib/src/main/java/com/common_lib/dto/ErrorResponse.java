package com.common_lib.dto;

import java.time.OffsetDateTime;

/**
 * Standardized API error contract.
 */
public record ErrorResponse(

        String code,
        String message,
        OffsetDateTime timestamp
) {}
