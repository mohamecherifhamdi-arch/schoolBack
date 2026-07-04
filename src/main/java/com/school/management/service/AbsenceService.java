package com.school.management.service;

import com.school.management.dto.AbsenceDTO;
import com.school.management.entity.Absence;
import com.school.management.entity.Enseignant;
import com.school.management.repository.AbsenceRepository;
import com.school.management.repository.EnseignantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbsenceService {
    private static Long generatedId = Long.valueOf(0);
    private final AbsenceRepository repository;
    private final EnseignantRepository enseignantRepository;
    public AbsenceService(AbsenceRepository repository, EnseignantRepository enseignantRepository) {
        this.repository = repository;
        this.enseignantRepository = enseignantRepository;
    }
    public List<AbsenceDTO> findAll() { return repository.findAll().stream().map(AbsenceDTO::fromEntity).toList(); }
    public AbsenceDTO findById(Long id) { return AbsenceDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Absence not found"))); }
    public AbsenceDTO create(AbsenceDTO dto) {
        Absence absence = new Absence();
        generatedId = generatedId +1;
        absence.setId(generatedId);
        absence.setEnseignant(dto.getEnseignant() != null && dto.getEnseignant().getId() != null ? enseignantRepository.findById(dto.getEnseignant().getId()).orElse(null) : null);
        absence.setClasse(dto.getClasse());
        absence.setSalle(dto.getSalle());
        absence.setDate(dto.getDate());
        absence.setSeance(dto.getSeance());
        absence.setJustifie(dto.isJustifie());
        absence.setMotif(dto.getMotif());
        Absence saved = repository.save(absence);
        if (saved.getEnseignant() != null) syncEnseignantAbsences(saved.getEnseignant().getId());
        return AbsenceDTO.fromEntity(saved);
    }
    public AbsenceDTO update(Long id, AbsenceDTO dto) {
        Absence existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Absence not found"));
        Long oldEnseignantId = existing.getEnseignant() != null ? existing.getEnseignant().getId() : null;
        existing.setDate(dto.getDate());
        existing.setSeance(dto.getSeance());
        existing.setJustifie(dto.isJustifie());
        existing.setMotif(dto.getMotif());
        existing.setEnseignant(dto.getEnseignant() != null && dto.getEnseignant().getId() != null ? enseignantRepository.findById(dto.getEnseignant().getId()).orElse(null) : null);
        existing.setClasse(dto.getClasse());
        existing.setSalle(dto.getSalle());
        Absence saved = repository.save(existing);
        if (saved.getEnseignant() != null) syncEnseignantAbsences(saved.getEnseignant().getId());
        if (oldEnseignantId != null && (saved.getEnseignant() == null || !oldEnseignantId.equals(saved.getEnseignant().getId()))) {
            syncEnseignantAbsences(oldEnseignantId);
        }
        return AbsenceDTO.fromEntity(saved);
    }
    public void delete(Long id) {
        Absence absence = repository.findById(id).orElseThrow(() -> new RuntimeException("Absence not found"));
        Long enseignantId = absence.getEnseignant() != null ? absence.getEnseignant().getId() : null;
        repository.deleteById(id);
        if (enseignantId != null) syncEnseignantAbsences(enseignantId);
    }
    public List<AbsenceDTO> findByEnseignant(Long enseignantId) { return repository.findByEnseignant_Id(enseignantId).stream().map(AbsenceDTO::fromEntity).toList(); }

    private void syncEnseignantAbsences(Long enseignantId) {
        Optional<Enseignant> opt = enseignantRepository.findById(enseignantId);
        if (opt.isPresent()) {
            Enseignant e = opt.get();
            e.setAbsences(repository.findByEnseignant_Id(enseignantId));
            enseignantRepository.save(e);
        }
    }
}
