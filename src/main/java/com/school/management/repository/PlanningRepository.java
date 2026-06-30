package com.school.management.repository;

import com.school.management.entity.Planning;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlanningRepository extends MongoRepository<Planning, Long> {
    List<Planning> findByEnseignant(String enseignant);
    List<Planning> findByClasse(String classe);
    List<Planning> findByDate(LocalDate date);
    List<Planning> findByDateBetween(LocalDate debut, LocalDate fin);
}
