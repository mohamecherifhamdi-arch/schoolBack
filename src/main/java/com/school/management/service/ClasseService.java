package com.school.management.service;

import com.school.management.dto.ClasseDTO;
import com.school.management.entity.Classe;
import com.school.management.repository.ClasseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseService {
    private final ClasseRepository repository;
    public ClasseService(ClasseRepository repository) { this.repository = repository; }

    public List<ClasseDTO> findAll() { return repository.findAll().stream().map(ClasseDTO::fromEntity).toList(); }
    public ClasseDTO findById(Long id) { return ClasseDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Classe not found"))); }
    public ClasseDTO create(ClasseDTO dto) {
        Classe c = new Classe();
        c.setNom(dto.getNom());
        c.setNiveau(dto.getNiveau());
        c.setFiliere(dto.getFiliere());
        c.setEffectif(dto.getEffectif());
        c.setAnneeScolaire(dto.getAnneeScolaire());
        return ClasseDTO.fromEntity(repository.save(c));
    }
    public ClasseDTO update(Long id, ClasseDTO dto) {
        Classe existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Classe not found"));
        existing.setNom(dto.getNom());
        existing.setNiveau(dto.getNiveau());
        existing.setFiliere(dto.getFiliere());
        existing.setEffectif(dto.getEffectif());
        existing.setAnneeScolaire(dto.getAnneeScolaire());
        return ClasseDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
