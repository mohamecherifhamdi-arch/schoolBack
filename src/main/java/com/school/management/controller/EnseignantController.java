package com.school.management.controller;

import com.school.management.dto.EnseignantDTO;
import com.school.management.service.EnseignantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {
    private final EnseignantService service;
    public EnseignantController(EnseignantService service) { this.service = service; }

    @GetMapping
    public List<EnseignantDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EnseignantDTO> create(@Valid @RequestBody EnseignantDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnseignantDTO> update(@PathVariable Long id, @Valid @RequestBody EnseignantDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
