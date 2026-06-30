package com.school.management.controller;

import com.school.management.dto.AbsenceDTO;
import com.school.management.service.AbsenceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absences")
public class AbsenceController {
    private final AbsenceService service;
    public AbsenceController(AbsenceService service) { this.service = service; }

    @GetMapping
    public List<AbsenceDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AbsenceDTO> create(@Valid @RequestBody AbsenceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceDTO> update(@PathVariable Long id, @Valid @RequestBody AbsenceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/enseignant/{enseignantId}")
    public List<AbsenceDTO> findByEnseignant(@PathVariable Long enseignantId) {
        return service.findByEnseignant(enseignantId);
    }
}
