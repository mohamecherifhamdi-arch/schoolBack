package com.school.management.service;

import com.school.management.dto.AuthRequestMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthRequestProducer {

    private final KafkaTemplate<String, AuthRequestMessage> kafkaTemplate;
    private static final String TOPIC = "auth-requests";

    public AuthRequestProducer(KafkaTemplate<String, AuthRequestMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(AuthRequestMessage message) {
        kafkaTemplate.send(TOPIC, message.getCorrelationId(), message);
    }
}
