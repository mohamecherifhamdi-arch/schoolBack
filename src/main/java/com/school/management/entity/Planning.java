package com.school.management.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "plannings")
public class Planning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String enseignant;////////////////////////***
    private String matiere; ////////////////////////***
    private String classe;////////////////////////***
    private String salle;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String type;
}
