package com.school.management.controller;

import com.school.management.dto.ReclamationDTO;
import com.school.management.service.ReclamationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations")
public class ReclamationController {
    private final ReclamationService service;
    public ReclamationController(ReclamationService service) { this.service = service; }

    @GetMapping
    public List<ReclamationDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ReclamationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReclamationDTO> create(@Valid @RequestBody ReclamationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReclamationDTO> update(@PathVariable Long id, @Valid @RequestBody ReclamationDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statut/{statut}")
    public List<ReclamationDTO> findByStatut(@PathVariable String statut) {
        return service.findByStatut(statut);
    }
}
