package com.school.management.service;

import com.school.management.dto.EnseignantDTO;
import com.school.management.entity.Enseignant;
import com.school.management.repository.EnseignantRepository;
import com.school.management.repository.MatiereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnseignantService {
    private final EnseignantRepository repository;
    private final MatiereRepository matiereRepository;
    public EnseignantService(EnseignantRepository repository, MatiereRepository matiereRepository) {
        this.repository = repository;
        this.matiereRepository = matiereRepository;
    }

    public List<EnseignantDTO> findAll() { return repository.findAll().stream().map(EnseignantDTO::fromEntity).toList(); }
    public EnseignantDTO findById(Long id) { return EnseignantDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Enseignant not found"))); }
    public EnseignantDTO create(EnseignantDTO dto) {
        Enseignant enseignant = new Enseignant();
        enseignant.setNom(dto.getNom());
        enseignant.setPrenom(dto.getPrenom());
        enseignant.setEmail(dto.getEmail());
        enseignant.setTelephone(dto.getTelephone());
        enseignant.setMatieres(dto.getMatieres());
        enseignant.setStatut(dto.getStatut());
        return EnseignantDTO.fromEntity(repository.save(enseignant));
    }
    public EnseignantDTO update(Long id, EnseignantDTO dto) {
        Enseignant existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Enseignant not found"));
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());
        existing.setMatieres(dto.getMatieres());
        existing.setStatut(dto.getStatut());
        return EnseignantDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) { repository.deleteById(id); }

}
