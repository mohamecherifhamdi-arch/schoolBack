package com.school.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "matieres")
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String code;
    @NotBlank private String nom;
    private String niveau;
    @JsonIgnoreProperties({"matieres", "absences"})
    private Enseignant enseignant;
    @JsonIgnoreProperties({"matieres", "eleves"})
    private Classe classe;
    private int coefficient;
    private int credit;
    private int heures;
    private String statut;
}
