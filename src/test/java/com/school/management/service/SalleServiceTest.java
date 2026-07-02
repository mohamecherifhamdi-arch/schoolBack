package com.school.management.service;

import com.school.management.dto.SalleDTO;
import com.school.management.entity.Salle;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/*
 * NOTE: Ce test suppose que Salle / SalleDTO sont des POJO classiques
 * (constructeur vide + getters/setters) et que SalleDTO.fromEntity(Salle)
 * copie simplement les champs. Adapte les setters si la structure réelle diffère.
 */
@ExtendWith(MockitoExtension.class)
class SalleServiceTest {

    @Mock
    private SalleRepository repository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private SalleService salleService;

    private Salle salle;
    private SalleDTO salleDTO;

    @BeforeEach
    void setUp() {
        salle = new Salle();
        salle.setId(1L);
        salle.setNom("Salle Info A");
        salle.setCapacite(30);
        salle.setBatiment("B1");
        salle.setEtage(2);
        salle.setStatut("DISPONIBLE");
        salle.setEquipement(List.of("Projecteur"));
        salle.setCode("SALLE_INFO");

        salleDTO = new SalleDTO();
        salleDTO.setNom("Salle Info A");
        salleDTO.setCapacite(30);
        salleDTO.setBatiment("B1");
        salleDTO.setEtage(2);
        salleDTO.setStatut("DISPONIBLE");
        salleDTO.setEquipement(List.of("Projecteur"));
    }

    @Test
    void findAll_returnsMappedList() {
        when(repository.findAll()).thenReturn(List.of(salle));

        List<SalleDTO> result = salleService.findAll();

        assertEquals(1, result.size());
        assertEquals("Salle Info A", result.get(0).getNom());
        verify(repository).findAll();
    }

    @Test
    void findAll_emptyRepository_returnsEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<SalleDTO> result = salleService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_found_returnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(salle));

        SalleDTO result = salleService.findById(1L);

        assertNotNull(result);
        assertEquals("Salle Info A", result.getNom());
    }

    @Test
    void findById_notFound_throwsRuntimeException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> salleService.findById(99L));
        assertEquals("Salle not found", ex.getMessage());
    }

    @Test
    void create_codeNotYetUsed_generatesBaseCode() {
        // nom "Salle Info A" -> substring(0,10) = "Salle Info" -> upper -> "SALLE INFO" -> replace spaces -> "SALLE_INFO"
        when(repository.findByCode("SALLE_INFO")).thenReturn(Optional.empty());
        when(repository.save(any(Salle.class))).thenAnswer(inv -> {
            Salle s = inv.getArgument(0);
            s.setId(10L);
            return s;
        });

        SalleDTO created = salleService.create(salleDTO);

        ArgumentCaptor<Salle> captor = ArgumentCaptor.forClass(Salle.class);
        verify(repository).save(captor.capture());
        assertEquals("SALLE_INFO", captor.getValue().getCode());
        assertEquals("Salle Info A", created.getNom());
    }

    @Test
    void create_codeAlreadyUsed_appendsSuffix() {
        when(repository.findByCode("SALLE_INFO")).thenReturn(Optional.of(salle));
        when(repository.findByCode("SALLE_INFO_1")).thenReturn(Optional.empty());
        when(repository.save(any(Salle.class))).thenAnswer(inv -> inv.getArgument(0));

        salleService.create(salleDTO);

        ArgumentCaptor<Salle> captor = ArgumentCaptor.forClass(Salle.class);
        verify(repository).save(captor.capture());
        assertEquals("SALLE_INFO_1", captor.getValue().getCode());
        verify(repository, times(1)).findByCode("SALLE_INFO");
        verify(repository, times(1)).findByCode("SALLE_INFO_1");
    }

    @Test
    void create_codeUsedTwice_incrementsSuffixTwice() {
        when(repository.findByCode("SALLE_INFO")).thenReturn(Optional.of(salle));
        when(repository.findByCode("SALLE_INFO_1")).thenReturn(Optional.of(salle));
        when(repository.findByCode("SALLE_INFO_2")).thenReturn(Optional.empty());
        when(repository.save(any(Salle.class))).thenAnswer(inv -> inv.getArgument(0));

        salleService.create(salleDTO);

        ArgumentCaptor<Salle> captor = ArgumentCaptor.forClass(Salle.class);
        verify(repository).save(captor.capture());
        assertEquals("SALLE_INFO_2", captor.getValue().getCode());
    }

    @Test
    void create_nomShorterThan10Chars_usesFullNomAsBaseCode() {
        salleDTO.setNom("A102");
        when(repository.findByCode("A102")).thenReturn(Optional.empty());
        when(repository.save(any(Salle.class))).thenAnswer(inv -> inv.getArgument(0));

        salleService.create(salleDTO);

        ArgumentCaptor<Salle> captor = ArgumentCaptor.forClass(Salle.class);
        verify(repository).save(captor.capture());
        assertEquals("A102", captor.getValue().getCode());
    }

    @Test
    void update_existingSalle_updatesAndSaves() {
        when(repository.findById(1L)).thenReturn(Optional.of(salle));
        when(repository.save(any(Salle.class))).thenAnswer(inv -> inv.getArgument(0));

        SalleDTO updateDto = new SalleDTO();
        updateDto.setNom("Salle Modifiee");
        updateDto.setCapacite(50);
        updateDto.setBatiment("B2");
        updateDto.setEtage(3);
        updateDto.setStatut("MAINTENANCE");
        updateDto.setEquipement(List.of("Tableau interactif"));

        SalleDTO result = salleService.update(1L, updateDto);

        assertEquals("Salle Modifiee", result.getNom());
        assertEquals(50, result.getCapacite());
        assertEquals("MAINTENANCE", result.getStatut());
        // code should not be touched by update()
        verify(repository, never()).findByCode(any());
    }

    @Test
    void update_notFound_throwsRuntimeException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> salleService.update(99L, salleDTO));
        verify(repository, never()).save(any());
    }

    @Test
    void delete_removesReservationsThenSalle() {
        when(reservationRepository.findBySalle_Id(1L)).thenReturn(Collections.emptyList());

        salleService.delete(1L);

        verify(reservationRepository).findBySalle_Id(1L);
        verify(reservationRepository).deleteAll(Collections.emptyList());
        verify(repository).deleteById(1L);
    }
}
