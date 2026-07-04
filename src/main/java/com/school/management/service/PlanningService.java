package com.school.management.service;

import com.school.management.dto.PlanningDTO;
import com.school.management.entity.Planning;
import com.school.management.repository.PlanningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlanningService {
    private Long paymentId = 1L;
    private final PlanningRepository repository;
    public PlanningService(PlanningRepository repository) {
        this.repository = repository;
    }

    public List<PlanningDTO> findAll() { return repository.findAll().stream().map(PlanningDTO::fromEntity).toList(); }
    public PlanningDTO findById(Long id) { return PlanningDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Planning not found"))); }
    public PlanningDTO create(PlanningDTO dto) {
        Planning p = new Planning();
        paymentId = paymentId +1;
        p.setId(paymentId);
        p.setEnseignant(dto.getEnseignant());
        p.setMatiere(dto.getMatiere());
        p.setClasse(dto.getClasse());
        p.setSalle(dto.getSalle());
        p.setDate(dto.getDate());
        p.setHeureDebut(dto.getHeureDebut());
        p.setHeureFin(dto.getHeureFin());
        p.setType(dto.getType());
        return PlanningDTO.fromEntity(repository.save(p));
    }
    public PlanningDTO update(Long id, PlanningDTO dto) {
        Planning existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Planning not found"));
        existing.setEnseignant(dto.getEnseignant());
        existing.setMatiere(dto.getMatiere());
        existing.setClasse(dto.getClasse());
        existing.setSalle(dto.getSalle());
        existing.setDate(dto.getDate());
        existing.setHeureDebut(dto.getHeureDebut());
        existing.setHeureFin(dto.getHeureFin());
        existing.setType(dto.getType());
        return PlanningDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) { repository.deleteById(id); }
    public List<PlanningDTO> findByDate(LocalDate date) { return repository.findByDate(date).stream().map(PlanningDTO::fromEntity).toList(); }
    public List<PlanningDTO> findByDateBetween(LocalDate debut, LocalDate fin) { return repository.findByDateBetween(debut, fin).stream().map(PlanningDTO::fromEntity).toList(); }
}
