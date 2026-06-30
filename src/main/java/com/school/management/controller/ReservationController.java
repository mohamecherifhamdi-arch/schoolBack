package com.school.management.controller;

import com.school.management.dto.ReservationDTO;
import com.school.management.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService service;
    public ReservationController(ReservationService service) { this.service = service; }

    @GetMapping
    public List<ReservationDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> create(@Valid @RequestBody ReservationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> update(@PathVariable Long id, @Valid @RequestBody ReservationDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salle/{salleId}")
    public List<ReservationDTO> getBySalle(@PathVariable Long salleId) {
        return service.findBySalleId(salleId);
    }

    @GetMapping("/date/{date}")
    public List<ReservationDTO> getByDate(@PathVariable String date) {
        return service.findByDate(date);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkConflict(
            @RequestParam Long salleId,
            @RequestParam String date,
            @RequestParam String seance) {
        List<ReservationDTO> conflicts = service.findByRoomIdAndDateAndSession(salleId, date, seance);
        return ResponseEntity.ok(!conflicts.isEmpty());
    }
}
