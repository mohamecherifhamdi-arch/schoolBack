package com.school.management.repository;

import com.school.management.entity.Parent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends MongoRepository<Parent, Long> {
    List<Parent> findByNomContainingIgnoreCase(String nom);
    List<Parent> findByStatut(String statut);
}
