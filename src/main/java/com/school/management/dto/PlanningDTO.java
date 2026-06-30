package com.school.management.dto;

import com.school.management.entity.Planning;
import java.time.LocalDate;
import java.time.LocalTime;

public class PlanningDTO {
    private Long id;
    private String enseignant;
    private String matiere;
    private String classe;
    private String salle;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String type;

    public PlanningDTO() {}

    public PlanningDTO(Long id, String enseignant, String matiere, String classe, String salle, LocalDate date, LocalTime heureDebut, LocalTime heureFin, String type) {
        this.id = id; this.enseignant = enseignant; this.matiere = matiere; this.classe = classe;
        this.salle = salle; this.date = date; this.heureDebut = heureDebut; this.heureFin = heureFin; this.type = type;
    }

    public static PlanningDTO fromEntity(Planning p) {
        return new PlanningDTO(p.getId(), p.getEnseignant(), p.getMatiere(), p.getClasse(), p.getSalle(), p.getDate(), p.getHeureDebut(), p.getHeureFin(), p.getType());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEnseignant() { return enseignant; }
    public void setEnseignant(String enseignant) { this.enseignant = enseignant; }
    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public String getSalle() { return salle; }
    public void setSalle(String salle) { this.salle = salle; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }
    public LocalTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
