package com.school.management.repository;

import com.school.management.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, Long> {
    List<Note> findByEleve_Id(Long eleveId);
    List<Note> findByMatiere_Id(Long matiereId);
    List<Note> findByClasse_Id(Long classeId);
    List<Note> findByEleve_IdAndMatiere_Id(Long eleveId, Long matiereId);
}
