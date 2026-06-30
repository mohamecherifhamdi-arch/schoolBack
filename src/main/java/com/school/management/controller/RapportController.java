package com.school.management.controller;

import com.school.management.dto.RapportDTO;
import com.school.management.service.RapportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rapports")
public class RapportController {
    private final RapportService service;
    public RapportController(RapportService service) { this.service = service; }

    @GetMapping
    public List<RapportDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<RapportDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RapportDTO> create(@Valid @RequestBody RapportDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RapportDTO> update(@PathVariable Long id, @Valid @RequestBody RapportDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statut/{statut}")
    public List<RapportDTO> findByStatut(@PathVariable String statut) {
        return service.findByStatut(statut);
    }
}
