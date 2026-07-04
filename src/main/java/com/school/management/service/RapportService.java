package com.school.management.service;

import com.school.management.dto.RapportDTO;
import com.school.management.entity.Rapport;
import com.school.management.repository.EleveRepository;
import com.school.management.repository.RapportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RapportService {
    private final RapportRepository repository;
    private final EleveRepository eleveRepository;
    private Long rapportId = 1L;
    public RapportService(RapportRepository repository, EleveRepository eleveRepository) { this.repository = repository; this.eleveRepository = eleveRepository; }

    public List<RapportDTO> findAll() { return repository.findAll().stream().map(RapportDTO::fromEntity).toList(); }
    public RapportDTO findById(Long id) { return RapportDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Rapport not found"))); }
    public RapportDTO create(RapportDTO dto) {
        Rapport r = new Rapport();
        rapportId = rapportId + 1;
        r.setId(rapportId);
        r.setTitre(dto.getTitre());
        r.setEleve(dto.getEleve() != null && dto.getEleve().getId() != null ? eleveRepository.findById(dto.getEleve().getId()).orElse(null) : null);
        r.setJurys(dto.getJurys());
        r.setRemarque(dto.getRemarque());
        r.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        r.setStatut(dto.getStatut());
        r.setBibliotheque(dto.isBibliotheque());
        return RapportDTO.fromEntity(repository.save(r));
    }
    public RapportDTO update(Long id, RapportDTO dto) {
        Rapport existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Rapport not found"));
        existing.setTitre(dto.getTitre());
        existing.setEleve(dto.getEleve() != null && dto.getEleve().getId() != null ? eleveRepository.findById(dto.getEleve().getId()).orElse(null) : null);
        existing.setJurys(dto.getJurys());
        existing.setRemarque(dto.getRemarque());
        existing.setStatut(dto.getStatut());
        existing.setBibliotheque(dto.isBibliotheque());
        return RapportDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) { repository.deleteById(id); }
    public List<RapportDTO> findByStatut(String statut) { return repository.findByStatut(statut).stream().map(RapportDTO::fromEntity).toList(); }
}
