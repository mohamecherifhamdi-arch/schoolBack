package com.school.management.controller;

import com.school.management.dto.ParentDTO;
import com.school.management.service.ParentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {
    private final ParentService service;
    public ParentController(ParentService service) { this.service = service; }

    @GetMapping
    public List<ParentDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ParentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParentDTO> create(@Valid @RequestBody ParentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentDTO> update(@PathVariable Long id, @Valid @RequestBody ParentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<ParentDTO> search(@RequestParam String nom) {
        return service.search(nom);
    }
}
