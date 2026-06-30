package com.school.management.service;

import com.school.management.dto.ReservationDTO;
import com.school.management.entity.Reservation;
import com.school.management.repository.EnseignantRepository;
import com.school.management.repository.ReservationRepository;
import com.school.management.repository.SalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final SalleRepository salleRepository;
    private final EnseignantRepository enseignantRepository;
    public ReservationService(ReservationRepository repository, SalleRepository salleRepository, EnseignantRepository enseignantRepository) {
        this.repository = repository;
        this.salleRepository = salleRepository;
        this.enseignantRepository = enseignantRepository;
    }

    public List<ReservationDTO> findAll() {
        return repository.findAll().stream().map(ReservationDTO::fromEntity).toList();
    }
    public ReservationDTO findById(Long id) {
        return ReservationDTO.fromEntity(repository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found")));
    }
    public ReservationDTO create(ReservationDTO dto) {
        Reservation r = new Reservation();
        r.setEnseignant(dto.getEnseignant() != null && dto.getEnseignant().getId() != null ? enseignantRepository.findById(dto.getEnseignant().getId()).orElse(null) : null);
        r.setSalle(dto.getSalle() != null && dto.getSalle().getId() != null ? salleRepository.findById(dto.getSalle().getId()).orElse(null) : null);
        r.setClassName(dto.getClassName());
        r.setDate(dto.getDate());
        r.setSession(dto.getSession());
        r.setSubject(dto.getSubject());
        return ReservationDTO.fromEntity(repository.save(r));
    }
    public ReservationDTO update(Long id, ReservationDTO dto) {
        Reservation existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        existing.setEnseignant(dto.getEnseignant() != null && dto.getEnseignant().getId() != null ? enseignantRepository.findById(dto.getEnseignant().getId()).orElse(null) : null);
        existing.setSalle(dto.getSalle() != null && dto.getSalle().getId() != null ? salleRepository.findById(dto.getSalle().getId()).orElse(null) : null);
        existing.setClassName(dto.getClassName());
        existing.setDate(dto.getDate());
        existing.setSession(dto.getSession());
        existing.setSubject(dto.getSubject());
        return ReservationDTO.fromEntity(repository.save(existing));
    }
    public void delete(Long id) { repository.deleteById(id); }
    public List<ReservationDTO> findBySalleId(Long salleId) {
        return repository.findBySalle_Id(salleId).stream().map(ReservationDTO::fromEntity).toList();
    }
    public List<ReservationDTO> findByDate(String date) {
        return repository.findByDate(date).stream().map(ReservationDTO::fromEntity).toList();
    }
    public List<ReservationDTO> findByRoomIdAndDateAndSession(Long salleId, String date, String session) {
        return repository.findBySalle_IdAndDateAndSession(salleId, date, session).stream().map(ReservationDTO::fromEntity).toList();
    }

}
