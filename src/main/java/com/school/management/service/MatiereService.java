package com.school.management.service;

import com.school.management.dto.MatiereDTO;
import com.school.management.entity.Classe;
import com.school.management.entity.Enseignant;
import com.school.management.entity.Matiere;
import com.school.management.repository.ClasseRepository;
import com.school.management.repository.EnseignantRepository;
import com.school.management.repository.MatiereRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatiereService {
    private final MatiereRepository repository;
    private final EnseignantRepository enseignantRepository;
    private final ClasseRepository classeRepository;
    public MatiereService(MatiereRepository repository, EnseignantRepository enseignantRepository, ClasseRepository classeRepository) {
        this.repository = repository;
        this.enseignantRepository = enseignantRepository;
        this.classeRepository = classeRepository;
    }

    public List<MatiereDTO> findAll() { return repository.findAll().stream().map(MatiereDTO::fromEntity).toList(); }
    public MatiereDTO findById(Long id) { return MatiereDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Matiere not found"))); }

    public MatiereDTO create(MatiereDTO dto) {
        Matiere matiere = new Matiere();
        matiere.setCode(dto.getCode());
        matiere.setNom(dto.getNom());
        matiere.setNiveau(dto.getNiveau());
        matiere.setEnseignant(dto.getEnseignant() != null && dto.getEnseignant().getId() != null ? enseignantRepository.findById(dto.getEnseignant().getId()).orElse(null) : null);
        matiere.setClasse(dto.getClasse() != null && dto.getClasse().getId() != null ? classeRepository.findById(dto.getClasse().getId()).orElse(null) : null);
        matiere.setCoefficient(dto.getCoefficient());
        matiere.setCredit(dto.getCredit());
        matiere.setHeures(dto.getHeures());
        matiere.setStatut(dto.getStatut());
        Matiere saved = repository.save(matiere);
        syncClasseMatieres(saved.getClasse());
        syncEnseignantMatieres(saved.getEnseignant());
        return MatiereDTO.fromEntity(saved);
    }

    public MatiereDTO update(Long id, MatiereDTO dto) {
        Matiere existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Matiere not found"));
        Classe oldClasse = existing.getClasse();
        Enseignant oldEnseignant = existing.getEnseignant();
        existing.setCode(dto.getCode());
        existing.setNom(dto.getNom());
        existing.setNiveau(dto.getNiveau());
        existing.setEnseignant(dto.getEnseignant() != null && dto.getEnseignant().getId() != null ? enseignantRepository.findById(dto.getEnseignant().getId()).orElse(null) : null);
        existing.setClasse(dto.getClasse() != null && dto.getClasse().getId() != null ? classeRepository.findById(dto.getClasse().getId()).orElse(null) : null);
        existing.setCoefficient(dto.getCoefficient());
        existing.setCredit(dto.getCredit());
        existing.setHeures(dto.getHeures());
        existing.setStatut(dto.getStatut());
        Matiere saved = repository.save(existing);
        syncClasseMatieres(saved.getClasse());
        if (oldClasse != null && (saved.getClasse() == null || !oldClasse.getId().equals(saved.getClasse().getId()))) {
            syncClasseMatieres(oldClasse);
        }
        syncEnseignantMatieres(saved.getEnseignant());
        if (oldEnseignant != null && (saved.getEnseignant() == null || !oldEnseignant.getId().equals(saved.getEnseignant().getId()))) {
            syncEnseignantMatieres(oldEnseignant);
        }
        return MatiereDTO.fromEntity(saved);
    }

    public void delete(Long id) {
        Matiere matiere = repository.findById(id).orElseThrow(() -> new RuntimeException("Matiere not found"));
        Classe classe = matiere.getClasse();
        Enseignant enseignant = matiere.getEnseignant();
        repository.deleteById(id);
        if (classe != null) syncClasseMatieres(classe);
        if (enseignant != null) syncEnseignantMatieres(enseignant);
    }

    private void syncClasseMatieres(Classe classe) {
        if (classe == null) return;
        Optional<Classe> opt = classeRepository.findById(classe.getId());
        if (opt.isPresent()) {
            Classe c = opt.get();
            c.setMatieres(repository.findByClasse_Id(classe.getId()));
            classeRepository.save(c);
        }
    }

    private void syncEnseignantMatieres(Enseignant enseignant) {
        if (enseignant == null) return;
        Optional<Enseignant> opt = enseignantRepository.findById(enseignant.getId());
        if (opt.isPresent()) {
            Enseignant e = opt.get();
            e.setMatieres(repository.findByEnseignant_Id(enseignant.getId()));
            enseignantRepository.save(e);
        }
    }
}
