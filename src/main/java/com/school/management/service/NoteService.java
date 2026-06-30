package com.school.management.service;

import com.school.management.dto.NoteDTO;
import com.school.management.entity.Eleve;
import com.school.management.entity.Note;
import com.school.management.repository.ClasseRepository;
import com.school.management.repository.EleveRepository;
import com.school.management.repository.MatiereRepository;
import com.school.management.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository repository;
    private final EleveRepository eleveRepository;
    private final MatiereRepository matiereRepository;
    private final ClasseRepository classeRepository;
    public NoteService(NoteRepository repository, EleveRepository eleveRepository, MatiereRepository matiereRepository, ClasseRepository classeRepository) {
        this.repository = repository;
        this.eleveRepository = eleveRepository;
        this.matiereRepository = matiereRepository;
        this.classeRepository = classeRepository;
    }

    public List<NoteDTO> findAll() { return repository.findAll().stream().map(NoteDTO::fromEntity).toList(); }
    public NoteDTO findById(Long id) { return NoteDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"))); }
    public NoteDTO create(NoteDTO dto) {
        Note note = new Note();
        note.setEleve(dto.getEleve() != null && dto.getEleve().getId() != null ? eleveRepository.findById(dto.getEleve().getId()).orElse(null) : null);
        note.setMatiere(dto.getMatiere() != null && dto.getMatiere().getId() != null ? matiereRepository.findById(dto.getMatiere().getId()).orElse(null) : null);
        note.setClasse(dto.getClasse() != null && dto.getClasse().getId() != null ? classeRepository.findById(dto.getClasse().getId()).orElse(null) : null);
        note.setType(dto.getType());
        note.setValeur(dto.getValeur());
        note.setCoefficient(dto.getCoefficient());
        note.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        note.setStatut(dto.getStatut());
        Note saved = repository.save(note);
        if (saved.getEleve() != null) syncEleveNotes(saved.getEleve().getId());
        return NoteDTO.fromEntity(saved);
    }
    public NoteDTO update(Long id, NoteDTO dto) {
        Note existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        Long oldEleveId = existing.getEleve() != null ? existing.getEleve().getId() : null;
        existing.setEleve(dto.getEleve() != null && dto.getEleve().getId() != null ? eleveRepository.findById(dto.getEleve().getId()).orElse(null) : null);
        existing.setMatiere(dto.getMatiere() != null && dto.getMatiere().getId() != null ? matiereRepository.findById(dto.getMatiere().getId()).orElse(null) : null);
        existing.setClasse(dto.getClasse() != null && dto.getClasse().getId() != null ? classeRepository.findById(dto.getClasse().getId()).orElse(null) : null);
        existing.setType(dto.getType());
        existing.setValeur(dto.getValeur());
        existing.setCoefficient(dto.getCoefficient());
        existing.setDate(dto.getDate());
        existing.setStatut(dto.getStatut());
        Note saved = repository.save(existing);
        if (saved.getEleve() != null) syncEleveNotes(saved.getEleve().getId());
        if (oldEleveId != null && (saved.getEleve() == null || !oldEleveId.equals(saved.getEleve().getId()))) {
            syncEleveNotes(oldEleveId);
        }
        return NoteDTO.fromEntity(saved);
    }
    public void delete(Long id) {
        Note note = repository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        Long eleveId = note.getEleve() != null ? note.getEleve().getId() : null;
        repository.deleteById(id);
        if (eleveId != null) syncEleveNotes(eleveId);
    }
    public List<NoteDTO> findByEleve(Long eleveId) { return repository.findByEleve_Id(eleveId).stream().map(NoteDTO::fromEntity).toList(); }
    public List<NoteDTO> findByClasse(Long classeId) { return repository.findByClasse_Id(classeId).stream().map(NoteDTO::fromEntity).toList(); }
    public List<NoteDTO> findByEleveAndMatiere(Long eleveId, Long matiereId) { return repository.findByEleve_IdAndMatiere_Id(eleveId, matiereId).stream().map(NoteDTO::fromEntity).toList(); }

    private void syncEleveNotes(Long eleveId) {
        Optional<Eleve> opt = eleveRepository.findById(eleveId);
        if (opt.isPresent()) {
            Eleve e = opt.get();
            e.setNotes(repository.findByEleve_Id(eleveId));
            eleveRepository.save(e);
        }
    }
}
