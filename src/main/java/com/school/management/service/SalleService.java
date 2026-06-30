package com.school.management.service;

import com.school.management.dto.SalleDTO;
import com.school.management.entity.Salle;
import com.school.management.repository.ReservationRepository;
import com.school.management.repository.SalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleService {
    private final SalleRepository repository;
    private final ReservationRepository reservationRepository;
    public SalleService(SalleRepository repository, ReservationRepository reservationRepository) { this.repository = repository; this.reservationRepository = reservationRepository; }

    public List<SalleDTO> findAll() {
        return repository.findAll().stream().map(SalleDTO::fromEntity).toList();
    }
    public SalleDTO findById(Long id) {
        return SalleDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Salle not found")));
    }
    public SalleDTO create(SalleDTO dto) {
        Salle salle = new Salle();
        salle.setNom(dto.getNom());
        salle.setCapacite(dto.getCapacite());
        salle.setBatiment(dto.getBatiment());
        salle.setEtage(dto.getEtage());
        salle.setStatut(dto.getStatut());
        salle.setEquipement(dto.getEquipement());
        String baseCode = salle.getNom().trim().substring(0, Math.min(salle.getNom().trim().length(), 10)).toUpperCase().replaceAll("\\s+", "_");
        String code = baseCode;
        int suffix = 1;
        while (repository.findByCode(code).isPresent()) {
            code = baseCode + "_" + suffix++;
        }
        salle.setCode(code);
        return SalleDTO.fromEntity(repository.save(salle));
    }
    public SalleDTO update(Long id, SalleDTO dto) {
        Salle existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Salle not found"));
        existing.setNom(dto.getNom());
        existing.setCapacite(dto.getCapacite());
        existing.setBatiment(dto.getBatiment());
        existing.setEtage(dto.getEtage());
        existing.setStatut(dto.getStatut());
        existing.setEquipement(dto.getEquipement());
        return SalleDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) {
        reservationRepository.deleteAll(reservationRepository.findBySalle_Id(id));
        repository.deleteById(id);
    }
}
