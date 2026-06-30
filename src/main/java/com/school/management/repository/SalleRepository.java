package com.school.management.repository;

import com.school.management.entity.Salle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalleRepository extends MongoRepository<Salle, Long> {
    List<Salle> findByStatut(String statut);
    List<Salle> findByBatiment(String batiment);
    List<Salle> findByCapaciteGreaterThanEqual(int capacite);
    java.util.Optional<Salle> findByCode(String code);
}
