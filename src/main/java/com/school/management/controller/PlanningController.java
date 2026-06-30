package com.school.management.controller;

import com.school.management.dto.PlanningDTO;
import com.school.management.service.PlanningService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/plannings")
public class PlanningController {
    private final PlanningService service;
    public PlanningController(PlanningService service) { this.service = service; }

    @GetMapping
    public List<PlanningDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<PlanningDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlanningDTO> create(@Valid @RequestBody PlanningDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanningDTO> update(@PathVariable Long id, @Valid @RequestBody PlanningDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date/{date}")
    public List<PlanningDTO> findByDate(@PathVariable String date) {
        return service.findByDate(LocalDate.parse(date));
    }

    @GetMapping("/range")
    public List<PlanningDTO> findByDateBetween(@RequestParam String debut, @RequestParam String fin) {
        return service.findByDateBetween(LocalDate.parse(debut), LocalDate.parse(fin));
    }
}
