package com.school.management.service;

import com.school.management.dto.PlanningDTO;
import com.school.management.entity.Planning;
import com.school.management.repository.PlanningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanningServiceTest {

    @Mock
    private PlanningRepository repository;

    @InjectMocks
    private PlanningService planningService;

    private Planning planning;
    private PlanningDTO planningDTO;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2026, 7, 1);

        planning = new Planning();
        planning.setId(1L);
        planning.setEnseignant("M. Dupont");
        planning.setMatiere("Mathematiques");
        planning.setClasse("L2-INFO");
        planning.setSalle("A101");
        planning.setDate(date);
        planning.setHeureDebut(LocalTime.of(8, 0));
        planning.setHeureFin(LocalTime.of(10, 0));
        planning.setType("COURS");

        planningDTO = new PlanningDTO();
        planningDTO.setEnseignant("M. Dupont");
        planningDTO.setMatiere("Mathematiques");
        planningDTO.setClasse("L2-INFO");
        planningDTO.setSalle("A101");
        planningDTO.setDate(date);
        planningDTO.setHeureDebut(LocalTime.of(8, 0));
        planningDTO.setHeureFin(LocalTime.of(10, 0));
        planningDTO.setType("COURS");
    }

    @Test
    void findAll_returnsMappedList() {
        when(repository.findAll()).thenReturn(List.of(planning));

        List<PlanningDTO> result = planningService.findAll();

        assertEquals(1, result.size());
        assertEquals("Mathematiques", result.get(0).getMatiere());
    }

    @Test
    void findAll_emptyRepository_returnsEmptyList() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(planningService.findAll().isEmpty());
    }

    @Test
    void findById_found_returnsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(planning));

        PlanningDTO result = planningService.findById(1L);

        assertNotNull(result);
        assertEquals("L2-INFO", result.getClasse());
    }

    @Test
    void findById_notFound_throwsRuntimeException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> planningService.findById(99L));
        assertEquals("Planning not found", ex.getMessage());
    }

    @Test
    void create_savesAndReturnsDto() {
        when(repository.save(any(Planning.class))).thenAnswer(inv -> {
            Planning p = inv.getArgument(0);
            p.setId(5L);
            return p;
        });

        PlanningDTO result = planningService.create(planningDTO);

        ArgumentCaptor<Planning> captor = ArgumentCaptor.forClass(Planning.class);
        verify(repository).save(captor.capture());
        assertEquals("Mathematiques", captor.getValue().getMatiere());
        assertEquals("COURS", result.getType());
    }

    @Test
    void update_existingPlanning_updatesFields() {
        when(repository.findById(1L)).thenReturn(Optional.of(planning));
        when(repository.save(any(Planning.class))).thenAnswer(inv -> inv.getArgument(0));

        PlanningDTO updateDto = new PlanningDTO();
        updateDto.setEnseignant("Mme Martin");
        updateDto.setMatiere("Physique");
        updateDto.setClasse("L3-INFO");
        updateDto.setSalle("B202");
        updateDto.setDate(date.plusDays(1));
        updateDto.setHeureDebut(LocalTime.of(14, 0));
        updateDto.setHeureFin(LocalTime.of(16, 0));
        updateDto.setType("TD");

        PlanningDTO result = planningService.update(1L, updateDto);

        assertEquals("Mme Martin", result.getEnseignant());
        assertEquals("Physique", result.getMatiere());
        assertEquals("TD", result.getType());
    }

    @Test
    void update_notFound_throwsRuntimeException() {
        when(repository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> planningService.update(404L, planningDTO));
        verify(repository, never()).save(any());
    }

    @Test
    void delete_callsRepositoryDeleteById() {
        planningService.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void findByDate_returnsMappedList() {
        when(repository.findByDate(date)).thenReturn(List.of(planning));

        List<PlanningDTO> result = planningService.findByDate(date);

        assertEquals(1, result.size());
        assertEquals(date, result.get(0).getDate());
    }

    @Test
    void findByDate_noResults_returnsEmptyList() {
        when(repository.findByDate(date)).thenReturn(Collections.emptyList());

        assertTrue(planningService.findByDate(date).isEmpty());
    }

    @Test
    void findByDateBetween_returnsMappedList() {
        LocalDate debut = date;
        LocalDate fin = date.plusDays(7);
        when(repository.findByDateBetween(debut, fin)).thenReturn(List.of(planning));

        List<PlanningDTO> result = planningService.findByDateBetween(debut, fin);

        assertEquals(1, result.size());
        verify(repository).findByDateBetween(debut, fin);
    }
}