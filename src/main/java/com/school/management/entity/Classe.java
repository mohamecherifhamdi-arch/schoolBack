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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "classes")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nom;
    private String niveau;
    private String filiere;
    private int effectif;
    private String anneeScolaire;
    @JsonIgnoreProperties({"classe", "notes", "payments"})
    private List<Eleve> eleves;
    @JsonIgnoreProperties({"classe", "enseignant"})
    private List<Matiere> matieres;
}
