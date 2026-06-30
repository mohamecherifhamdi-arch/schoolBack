package com.school.management.repository;

import com.school.management.entity.Enseignant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnseignantRepository extends MongoRepository<Enseignant, Long> {
    List<Enseignant> findByNomContainingIgnoreCase(String nom);
    List<Enseignant> findByMatieres_Id(Long matiereId);
    List<Enseignant> findByStatut(String statut);
}
