package com.school.management.service;

import com.school.management.dto.EleveDTO;
import com.school.management.entity.Eleve;
import com.school.management.repository.EleveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EleveService {
    private Long eleveId = 1L;
    private final EleveRepository repository;
    private final EntitySyncService entitySyncService;
    public EleveService(EleveRepository repository, EntitySyncService entitySyncService) { this.repository = repository; this.entitySyncService = entitySyncService; }

    public List<EleveDTO> findAll() { return repository.findAll().stream().map(EleveDTO::fromEntity).toList(); }
    public EleveDTO findById(Long id) { return EleveDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Eleve not found"))); }
    public EleveDTO create(EleveDTO dto) {
        eleveId = eleveId + 1;
        return EleveDTO.fromEntity(repository.save(dto.toEntity(eleveId)));
    }
    public EleveDTO update(Long id, EleveDTO dto) {
        Eleve existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Eleve not found"));
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setClasse(dto.getClasse());
        existing.setNiveau(dto.getNiveau());
        existing.setTelephone(dto.getTelephone());
        existing.setEmail(dto.getEmail());
        existing.setStatut(dto.getStatut());
        Eleve saved = repository.save(existing);
        entitySyncService.syncEleve(saved);
        return EleveDTO.fromEntity(saved);
    }
    public void delete(Long id) { repository.deleteById(id);
        this.entitySyncService.syncDeleteEleve(id);
    }
    public List<EleveDTO> search(String nom) { return repository.findByNomContainingIgnoreCase(nom).stream().map(EleveDTO::fromEntity).toList(); }
}
