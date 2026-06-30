package com.school.management.repository;

import com.school.management.entity.Rapport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportRepository extends MongoRepository<Rapport, Long> {
    List<Rapport> findByStatut(String statut);
    List<Rapport> findByEleveNomContainingIgnoreCase(String eleveNom);
    List<Rapport> findByEleve_Id(Long eleveId);
}
