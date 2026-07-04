package com.school.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "absences")
public class Absence {
    @Id
   //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnoreProperties({"absences", "matiere"})
    private Enseignant enseignant;
    private String classe; ////////////////////////***
    private String salle;
    private LocalDate date;
    private String seance;
    private boolean justifie;
    private String motif;
}
