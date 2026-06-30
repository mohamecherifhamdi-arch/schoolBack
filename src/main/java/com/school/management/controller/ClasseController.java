package com.school.management.controller;

import com.school.management.dto.ClasseDTO;
import com.school.management.service.ClasseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {
    private final ClasseService service;
    public ClasseController(ClasseService service) { this.service = service; }

    @GetMapping
    public List<ClasseDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClasseDTO> create(@Valid @RequestBody ClasseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClasseDTO> update(@PathVariable Long id, @Valid @RequestBody ClasseDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
