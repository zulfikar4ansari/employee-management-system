package com.notification_service.provider;


import com.notification_service.model.NotificationRequest;

/**
 * Strategy interface for different channels.
 */
public interface NotificationProvider {

    ProviderType type();

    void send(NotificationRequest request);
}
