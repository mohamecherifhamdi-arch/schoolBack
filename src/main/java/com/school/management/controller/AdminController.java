package com.school.management.controller;

import com.school.management.entity.Admin;
import com.school.management.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService service;
    public AdminController(AdminService service) { this.service = service; }

    @GetMapping
    public List<Admin> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> update(@PathVariable String id, @Valid @RequestBody Admin admin) {
        return ResponseEntity.ok(service.update(id, admin));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public List<Admin> findByStatus(@PathVariable String status) {
        return service.findByStatus(status);
    }
}
