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

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rapports")
public class Rapport {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String titre;

    @JsonIgnoreProperties({"notes", "payments", "classe"})
    private Eleve eleve;
    private List<String> jurys; ////////////////////////***
    private String remarque;
    private LocalDate date;
    private String statut;
    private boolean bibliotheque;
}
