package com.school.management.controller;

import com.school.management.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final RestTemplate restTemplate;
    private final DashboardService dashboardService;
    private static final String BASE = "http://localhost:8082/api/dashboard";

    public DashboardController(RestTemplate restTemplate, DashboardService dashboardService) {
        this.restTemplate = restTemplate;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }

    @GetMapping("/teachers")
    public ResponseEntity<?> getTeachers() {
        try {
            Object response = restTemplate.getForObject(BASE + "/teachers", Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }


}
