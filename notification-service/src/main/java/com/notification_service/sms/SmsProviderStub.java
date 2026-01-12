package com.notification_service.sms;


import com.notification_service.model.NotificationRequest;
import com.notification_service.provider.NotificationProvider;
import com.notification_service.provider.ProviderType;
import org.springframework.stereotype.Component;

/**
 * Stub SMS provider.
 * Replace with Twilio / AWS SNS / MSG91 etc.
 */
@Component
public class SmsProviderStub implements NotificationProvider {

    @Override
    public ProviderType type() {
        return ProviderType.SMS;
    }

    @Override
    public void send(NotificationRequest request) {
        System.out.println("[SMS-STUB] To=" + request.toMobile() + " Msg=" + request.message());
    }
}
