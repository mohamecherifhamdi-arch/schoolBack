package com.school.management.service;

import com.school.management.dto.AuthResponseMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthResponseConsumer {

    private final ConcurrentHashMap<String, CompletableFuture<AuthResponseMessage>> pendingRequests = new ConcurrentHashMap<>();

    public CompletableFuture<AuthResponseMessage> registerPending(String correlationId) {
        CompletableFuture<AuthResponseMessage> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        return future;
    }

    @KafkaListener(topics = "auth-responses", containerFactory = "authResponseKafkaListenerFactory")
    public void consume(AuthResponseMessage message) {
        CompletableFuture<AuthResponseMessage> future = pendingRequests.remove(message.getCorrelationId());
        if (future != null) {
            future.complete(message);
        }
    }
}
