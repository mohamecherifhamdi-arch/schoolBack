package com.school.management.service;

import com.school.management.dto.ReservationDTO;
import com.school.management.entity.Enseignant;
import com.school.management.entity.Reservation;
import com.school.management.entity.Salle;
import com.school.management.repository.EnseignantRepository;
import com.school.management.repository.ReservationRepository;
import com.school.management.repository.SalleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
 * NOTE: Ce test suppose que ReservationDTO.getEnseignant()/getSalle() renvoient
 * des objets exposant au minimum getId()/setId() (ex: EnseignantDTO / SalleDTO).
 * Adapte les types/setters si la structure réelle diffère.
 */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository repository;

    @Mock
    private SalleRepository salleRepository;

    @Mock
    private EnseignantRepository enseignantRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private Salle salle;
    private Enseignant enseignant;

    @BeforeEach
    void setUp() {
        salle = new Salle();
        salle.setId(1L);
        salle.setNom("Salle Info A");

        enseignant = new Enseignant();
        enseignant.setId(2L);

        reservation = new Reservation();
        reservation.setId(100L);
        reservation.setSalle(salle);
        reservation.setEnseignant(enseignant);
        reservation.setClassName("L3-INFO");
        reservation.setDate("2026-07-01");
        reservation.setSession("MATIN");
        reservation.setSubject("Bases de donnees");
    }

    @Test
    void findAll_returnsMappedList() {
        when(repository.findAll()).thenReturn(List.of(reservation));

        List<ReservationDTO> result = reservationService.findAll();

        assertEquals(1, result.size());
        assertEquals("L3-INFO", result.get(0).getClassName());
    }

    @Test
    void findById_found_returnsDto() {
        when(repository.findById(100L)).thenReturn(Optional.of(reservation));

        ReservationDTO result = reservationService.findById(100L);

        assertNotNull(result);
        assertEquals("Bases de donnees", result.getSubject());
    }

    @Test
    void findById_notFound_throwsRuntimeException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> reservationService.findById(999L));
        assertEquals("Reservation not found", ex.getMessage());
    }

    @Test
    void create_withValidEnseignantAndSalleIds_resolvesBoth() {
        ReservationDTO dto = buildDto(2L, 1L);
        when(enseignantRepository.findById(2L)).thenReturn(Optional.of(enseignant));
        when(salleRepository.findById(1L)).thenReturn(Optional.of(salle));
        when(repository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        ReservationDTO result = reservationService.create(dto);

        assertEquals("L3-INFO", result.getClassName());
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repository).save(captor.capture());
        assertEquals(enseignant, captor.getValue().getEnseignant());
        assertEquals(salle, captor.getValue().getSalle());
    }

    @Test
    void create_withNullEnseignant_setsEnseignantNull() {
        ReservationDTO dto = buildDto(null, 1L);
        when(salleRepository.findById(1L)).thenReturn(Optional.of(salle));
        when(repository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        reservationService.create(dto);

        verify(enseignantRepository, never()).findById(any());
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repository).save(captor.capture());
        assertNull(captor.getValue().getEnseignant());
    }

    @Test
    void create_withNullSalle_setsSalleNull() {
        ReservationDTO dto = buildDto(2L, null);
        when(enseignantRepository.findById(2L)).thenReturn(Optional.of(enseignant));
        when(repository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        reservationService.create(dto);

        verify(salleRepository, never()).findById(any());
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repository).save(captor.capture());
        assertNull(captor.getValue().getSalle());
    }

    @Test
    void create_withUnknownEnseignantId_setsEnseignantNull() {
        ReservationDTO dto = buildDto(999L, 1L);
        when(enseignantRepository.findById(999L)).thenReturn(Optional.empty());
        when(salleRepository.findById(1L)).thenReturn(Optional.of(salle));
        when(repository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        reservationService.create(dto);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repository).save(captor.capture());
        assertNull(captor.getValue().getEnseignant());
    }

    @Test
    void update_existingReservation_updatesFields() {
        when(repository.findById(100L)).thenReturn(Optional.of(reservation));
        when(salleRepository.findById(1L)).thenReturn(Optional.of(salle));
        when(enseignantRepository.findById(2L)).thenReturn(Optional.of(enseignant));
        when(repository.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        ReservationDTO dto = buildDto(2L, 1L);
        dto.setSubject("Algorithmique");

        ReservationDTO result = reservationService.update(100L, dto);

        assertEquals("Algorithmique", result.getSubject());
    }

    @Test
    void update_notFound_throwsRuntimeException() {
        when(repository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationService.update(404L, buildDto(2L, 1L)));
        verify(repository, never()).save(any());
    }

    @Test
    void delete_callsRepositoryDeleteById() {
        reservationService.delete(100L);

        verify(repository).deleteById(100L);
    }

    @Test
    void findBySalleId_returnsMappedList() {
        when(repository.findBySalle_Id(1L)).thenReturn(List.of(reservation));

        List<ReservationDTO> result = reservationService.findBySalleId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void findBySalleId_noResults_returnsEmptyList() {
        when(repository.findBySalle_Id(1L)).thenReturn(Collections.emptyList());

        List<ReservationDTO> result = reservationService.findBySalleId(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByDate_returnsMappedList() {
        when(repository.findByDate("2026-07-01")).thenReturn(List.of(reservation));

        List<ReservationDTO> result = reservationService.findByDate("2026-07-01");

        assertEquals(1, result.size());
        assertEquals("2026-07-01", result.get(0).getDate());
    }

    @Test
    void findByRoomIdAndDateAndSession_returnsMappedList() {
        when(repository.findBySalle_IdAndDateAndSession(1L, "2026-07-01", "MATIN"))
                .thenReturn(List.of(reservation));

        List<ReservationDTO> result = reservationService.findByRoomIdAndDateAndSession(1L, "2026-07-01", "MATIN");

        assertEquals(1, result.size());
        assertEquals("MATIN", result.get(0).getSession());
    }

    // Helper to build a ReservationDTO with nested enseignant/salle id references.
    private ReservationDTO buildDto(Long enseignantId, Long salleId) {
        ReservationDTO dto = new ReservationDTO();
        dto.setClassName("L3-INFO");
        dto.setDate("2026-07-01");
        dto.setSession("MATIN");
        dto.setSubject("Bases de donnees");

        if (enseignantId != null) {
            var ens = new com.school.management.entity.Enseignant();
            ens.setId(enseignantId);
            dto.setEnseignant(ens);
        }
        if (salleId != null) {
            Salle salle = new Salle();
            salle.setId(salleId);
            dto.setSalle(salle);
        }
        return dto;
    }
}
