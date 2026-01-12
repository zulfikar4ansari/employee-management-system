package com.notification_service.model;

/**
 * Normalized notification request for sending via providers.
 */
public record NotificationRequest(
        String toMobile,
        String message
) {}
