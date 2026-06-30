package com.school.management.repository;

import com.school.management.entity.Matiere;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatiereRepository extends MongoRepository<Matiere, Long> {
    List<Matiere> findByNomContainingIgnoreCase(String nom);
    List<Matiere> findByNiveau(String niveau);
    List<Matiere> findByStatut(String statut);
    List<Matiere> findByClasse_Id(Long classeId);
    List<Matiere> findByEnseignant_Id(Long enseignantId);
}
