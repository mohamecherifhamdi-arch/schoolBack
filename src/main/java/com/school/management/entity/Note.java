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
@Document(collection = "notes")
public class Note {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnoreProperties({"notes", "payments", "classe"})
    private Eleve eleve;
    @JsonIgnoreProperties({"enseignant", "classe"})
    private Matiere matiere;
    @JsonIgnoreProperties({"eleves", "matieres"})
    private Classe classe;
    private String type;
    private double valeur;
    private double coefficient;
    private LocalDate date;
    private String statut;
}
