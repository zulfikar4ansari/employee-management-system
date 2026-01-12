package com.notification_service.service;


import com.notification_service.model.NotificationRequest;
import com.notification_service.provider.NotificationProvider;
import com.notification_service.provider.ProviderType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Picks the correct provider and sends notifications.
 */
@Service
public class NotificationDispatcher {

    private final List<NotificationProvider> providers;

    @Value("${notification.channels.whatsapp:true}")
    private boolean whatsappEnabled;

    @Value("${notification.channels.sms:false}")
    private boolean smsEnabled;

    public NotificationDispatcher(List<NotificationProvider> providers) {
        this.providers = providers;
    }

    public void sendWhatsApp(NotificationRequest request) {
        if (!whatsappEnabled) return;
        find(ProviderType.WHATSAPP).send(request);
    }

    public void sendSms(NotificationRequest request) {
        if (!smsEnabled) return;
        find(ProviderType.SMS).send(request);
    }

    private NotificationProvider find(ProviderType type) {
        return providers.stream()
                .filter(p -> p.type() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Provider not found: " + type));
    }
}
