package com.school.management.repository;

import com.school.management.entity.Eleve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EleveRepository extends MongoRepository<Eleve, Long> {
    List<Eleve> findByNomContainingIgnoreCase(String nom);
    List<Eleve> findByClasse(String classe);
    List<Eleve> findByStatut(String statut);
}
