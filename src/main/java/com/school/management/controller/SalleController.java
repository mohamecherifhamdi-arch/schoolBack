package com.school.management.controller;

import com.school.management.dto.SalleDTO;
import com.school.management.service.SalleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
public class SalleController {
    private final SalleService service;
    public SalleController(SalleService service) { this.service = service; }

    @GetMapping
    public List<SalleDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<SalleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SalleDTO> create(@Valid @RequestBody SalleDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalleDTO> update(@PathVariable Long id, @Valid @RequestBody SalleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
