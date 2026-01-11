package com.attendance_service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Publishes Kafka messages.
 */
@Component
public class KafkaPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${attendance.kafka-topic}")
    private String topic;

    public KafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishAttendanceNotification(AttendanceNotificationEvent event) {
        kafkaTemplate.send(topic, String.valueOf(event.employeeId()), event);
    }
}
