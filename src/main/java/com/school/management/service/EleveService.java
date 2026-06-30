package com.school.management.service;

import com.school.management.dto.EleveDTO;
import com.school.management.entity.Eleve;
import com.school.management.repository.EleveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EleveService {
    private final EleveRepository repository;
    public EleveService(EleveRepository repository) { this.repository = repository; }

    public List<EleveDTO> findAll() { return repository.findAll().stream().map(EleveDTO::fromEntity).toList(); }
    public EleveDTO findById(Long id) { return EleveDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Eleve not found"))); }
    public EleveDTO create(EleveDTO dto) { return EleveDTO.fromEntity(repository.save(dto.toEntity())); }
    public EleveDTO update(Long id, EleveDTO dto) {
        Eleve existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Eleve not found"));
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setClasse(dto.getClasse());
        existing.setNiveau(dto.getNiveau());
        existing.setTelephone(dto.getTelephone());
        existing.setEmail(dto.getEmail());
        existing.setStatut(dto.getStatut());
        return EleveDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) { repository.deleteById(id); }
    public List<EleveDTO> search(String nom) { return repository.findByNomContainingIgnoreCase(nom).stream().map(EleveDTO::fromEntity).toList(); }
}
