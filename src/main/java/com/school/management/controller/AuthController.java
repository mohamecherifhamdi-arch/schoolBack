package com.school.management.controller;

import com.school.management.dto.*;
import com.school.management.service.AuthRequestProducer;
import com.school.management.service.AuthResponseConsumer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthRequestProducer producer;
    private final AuthResponseConsumer responseConsumer;

    public AuthController(AuthRequestProducer producer, AuthResponseConsumer responseConsumer) {
        this.producer = producer;
        this.responseConsumer = responseConsumer;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Welcommmmmmmmmmmmmmmme to login: ");
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<AuthResponseMessage> future = responseConsumer.registerPending(correlationId);

        producer.send(new AuthRequestMessage(correlationId, "LOGIN", request.getUsername(), request.getPassword()));

        try {
            AuthResponseMessage response = future.get(10, TimeUnit.SECONDS);
            if (response.getError() != null) {
                return ResponseEntity.badRequest().body(response.getError());
            }
            return ResponseEntity.ok(new AuthResponse(response.getToken(), response.getRefreshToken(), response.getUsername(), response.getRole()));
        } catch (Exception e) {
            return ResponseEntity.status(504).body("Auth service timeout");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<AuthResponseMessage> future = responseConsumer.registerPending(correlationId);

        producer.send(new AuthRequestMessage(correlationId, "REGISTER", request.getUsername(), request.getPassword(), request.getRole(), token, request.getStatus()));

        try {
            AuthResponseMessage response = future.get(10, TimeUnit.SECONDS);
            if (response.getError() != null) {
                return ResponseEntity.badRequest().body(response.getError());
            }
            return ResponseEntity.ok(new AuthResponse(response.getToken(), response.getRefreshToken(), response.getUsername(), response.getRole()));
        } catch (Exception e) {
            return ResponseEntity.status(504).body("Auth service timeout");
        }
    }
}
