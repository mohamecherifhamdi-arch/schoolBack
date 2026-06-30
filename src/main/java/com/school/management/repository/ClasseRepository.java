package com.school.management.repository;

import com.school.management.entity.Classe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasseRepository extends MongoRepository<Classe, Long> {
    List<Classe> findByNiveau(String niveau);
    List<Classe> findByFiliere(String filiere);
}
