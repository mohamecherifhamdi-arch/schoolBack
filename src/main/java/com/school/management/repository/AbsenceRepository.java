package com.school.management.repository;

import com.school.management.entity.Absence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends MongoRepository<Absence, Long> {
    List<Absence> findByEnseignant_Id(Long enseignantId);
}
