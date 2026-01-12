package com.notification_service.kafka;


import com.common_lib.events.AttendanceNotificationEvent;
import com.notification_service.model.NotificationRequest;
import com.notification_service.service.NotificationDispatcher;
import com.notification_service.service.TemplateService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for attendance.notification topic.
 */
@Component
public class AttendanceNotificationConsumer {

    private final NotificationDispatcher dispatcher;
    private final TemplateService templateService;

    public AttendanceNotificationConsumer(NotificationDispatcher dispatcher,
                                         TemplateService templateService) {
        this.dispatcher = dispatcher;
        this.templateService = templateService;
    }

    @KafkaListener(topics = "${notification.topic}", groupId = "notification-service-group")
    public void consume(AttendanceNotificationEvent event) {

        // Employee notification
        if (event.employeeMobile() != null && !event.employeeMobile().isBlank()) {
            dispatcher.sendWhatsApp(new NotificationRequest(
                    event.employeeMobile(),
                    templateService.buildEmployeeMessage(event)
            ));
        }

        // HR notification
        if (event.hrMobile() != null && !event.hrMobile().isBlank()) {
            dispatcher.sendWhatsApp(new NotificationRequest(
                    event.hrMobile(),
                    templateService.buildHrMessage(event)
            ));
        }
    }
}
