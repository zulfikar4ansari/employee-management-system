package com.notification_service.whatsapp;


import com.notification_service.model.NotificationRequest;
import com.notification_service.provider.NotificationProvider;
import com.notification_service.provider.ProviderType;
import org.springframework.stereotype.Component;

/**
 * Stub WhatsApp provider.
 * Replace with Meta WhatsApp Business API / Twilio / Gupshup integration.
 */
@Component
public class WhatsAppProviderStub implements NotificationProvider {

    @Override
    public ProviderType type() {
        return ProviderType.WHATSAPP;
    }

    @Override
    public void send(NotificationRequest request) {
        System.out.println("[WHATSAPP-STUB] To=" + request.toMobile() + " Msg=" + request.message());
    }
}
