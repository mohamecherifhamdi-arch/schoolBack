package com.school.management.controller;

import com.school.management.dto.NoteDTO;
import com.school.management.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService service;
    public NoteController(NoteService service) { this.service = service; }

    @GetMapping
    public List<NoteDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> update(@PathVariable Long id, @Valid @RequestBody NoteDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eleve/{eleve}")
    public List<NoteDTO> findByEleve(@PathVariable Long eleve) {
        return service.findByEleve(eleve);
    }

    @GetMapping("/classe/{classe}")
    public List<NoteDTO> findByClasse(@PathVariable Long classe) {
        return service.findByClasse(classe);
    }
}
