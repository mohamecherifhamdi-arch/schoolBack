package com.school.management.controller;

import com.school.management.dto.MatiereDTO;
import com.school.management.service.MatiereService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matieres")
public class MatiereController {
    private final MatiereService service;
    public MatiereController(MatiereService service) { this.service = service; }

    @GetMapping
    public List<MatiereDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<MatiereDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MatiereDTO> create(@Valid @RequestBody MatiereDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatiereDTO> update(@PathVariable Long id, @Valid @RequestBody MatiereDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
