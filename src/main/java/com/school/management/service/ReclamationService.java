package com.school.management.service;

import com.school.management.dto.ReclamationDTO;
import com.school.management.entity.Reclamation;
import com.school.management.repository.EleveRepository;
import com.school.management.repository.EnseignantRepository;
import com.school.management.repository.ReclamationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReclamationService {
    private final ReclamationRepository repository;
    private final EnseignantRepository enseignantRepository;
    private final EleveRepository eleveRepository;
    public ReclamationService(ReclamationRepository repository, EnseignantRepository enseignantRepository, EleveRepository eleveRepository) {
        this.repository = repository;
        this.enseignantRepository = enseignantRepository;
        this.eleveRepository = eleveRepository;
    }

    public List<ReclamationDTO> findAll() { return repository.findAll().stream().map(ReclamationDTO::fromEntity).toList(); }
    public ReclamationDTO findById(Long id) { return ReclamationDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Reclamation not found"))); }
    public ReclamationDTO create(ReclamationDTO dto) {
        Reclamation r = new Reclamation();
        r.setEnseignant(dto.getEnseignant() != null && dto.getEnseignant().getId() != null ? enseignantRepository.findById(dto.getEnseignant().getId()).orElse(null) : null);
        r.setEleve(dto.getEleve() != null && dto.getEleve().getId() != null ? eleveRepository.findById(dto.getEleve().getId()).orElse(null) : null);
        r.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        r.setCause(dto.getCause());
        r.setPenalite(dto.getPenalite());
        r.setStatut(dto.getStatut());
        return ReclamationDTO.fromEntity(repository.save(r));
    }
    public ReclamationDTO update(Long id, ReclamationDTO dto) {
        Reclamation existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Reclamation not found"));
        existing.setEnseignant(dto.getEnseignant() != null && dto.getEnseignant().getId() != null ? enseignantRepository.findById(dto.getEnseignant().getId()).orElse(null) : null);
        existing.setEleve(dto.getEleve() != null && dto.getEleve().getId() != null ? eleveRepository.findById(dto.getEleve().getId()).orElse(null) : null);
        existing.setCause(dto.getCause());
        existing.setPenalite(dto.getPenalite());
        existing.setStatut(dto.getStatut());
        return ReclamationDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) { repository.deleteById(id); }
    public List<ReclamationDTO> findByStatut(String statut) { return repository.findByStatut(statut).stream().map(ReclamationDTO::fromEntity).toList(); }
}
