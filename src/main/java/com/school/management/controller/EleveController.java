package com.school.management.controller;

import com.school.management.dto.EleveDTO;
import com.school.management.service.EleveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eleves")
public class EleveController {
    private final EleveService service;
    public EleveController(EleveService service) { this.service = service; }

    @GetMapping
    public List<EleveDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<EleveDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EleveDTO> create(@Valid @RequestBody EleveDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EleveDTO> update(@PathVariable Long id, @Valid @RequestBody EleveDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<EleveDTO> search(@RequestParam String nom) {
        return service.search(nom);
    }
}
