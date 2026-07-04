package com.school.management.service;

import com.school.management.dto.ParentDTO;
import com.school.management.entity.Parent;
import com.school.management.repository.EleveRepository;
import com.school.management.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {
    private Long parentId = 1L;
    private final ParentRepository repository;
    private final EleveRepository eleveRepository;
    public ParentService(ParentRepository repository, EleveRepository eleveRepository) {
        this.repository = repository;
        this.eleveRepository = eleveRepository;
    }

    public List<ParentDTO> findAll() { return repository.findAll().stream().map(ParentDTO::fromEntity).toList(); }
    public ParentDTO findById(Long id) { return ParentDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Parent not found"))); }
    public ParentDTO create(ParentDTO dto) {
        Parent p = new Parent();
        parentId = parentId + 1;
        p.setId(parentId);
        p.setNom(dto.getNom());
        p.setAdresse(dto.getAdresse());
        p.setTelephone(dto.getTelephone());
        p.setEmail(dto.getEmail());
        p.setEleves(dto.getEleves());
        p.setStatut(dto.getStatut());
        return ParentDTO.fromEntity(repository.save(p));
    }
    public ParentDTO update(Long id, ParentDTO dto) {
        Parent existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Parent not found"));
        existing.setNom(dto.getNom());
        existing.setAdresse(dto.getAdresse());
        existing.setTelephone(dto.getTelephone());
        existing.setEmail(dto.getEmail());
        existing.setEleves(dto.getEleves());
        existing.setStatut(dto.getStatut());
        return ParentDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) { repository.deleteById(id); }
    public List<ParentDTO> search(String nom) { return repository.findByNomContainingIgnoreCase(nom).stream().map(ParentDTO::fromEntity).toList(); }

}
