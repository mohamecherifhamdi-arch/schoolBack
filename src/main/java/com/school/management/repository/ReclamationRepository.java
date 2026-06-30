package com.school.management.repository;

import com.school.management.entity.Reclamation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamationRepository extends MongoRepository<Reclamation, Long> {
    List<Reclamation> findByStatut(String statut);
}
