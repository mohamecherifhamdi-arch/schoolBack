package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Eleve;
import com.school.management.entity.Enseignant;
import com.school.management.entity.Reclamation;
import java.time.LocalDate;

public class ReclamationDTO {
    private Long id;
    @JsonIgnoreProperties({"matieres", "absences"})
    private Enseignant enseignant;
    @JsonIgnoreProperties({"payments", "notes", "parents"})
    private Eleve eleve;
    private LocalDate date;
    private String cause;
    private String penalite;
    private String statut;

    public ReclamationDTO() {}

    public ReclamationDTO(Long id, Enseignant enseignant, Eleve eleve, LocalDate date, String cause, String penalite, String statut) {
        this.id = id; this.enseignant = enseignant; this.eleve = eleve;
        this.date = date; this.cause = cause; this.penalite = penalite; this.statut = statut;
    }

    public static ReclamationDTO fromEntity(Reclamation r) {
        return new ReclamationDTO(r.getId(), r.getEnseignant(), r.getEleve(),
                r.getDate(), r.getCause(), r.getPenalite(), r.getStatut());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }
    public Eleve getEleve() { return eleve; }
    public void setEleve(Eleve eleve) { this.eleve = eleve; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getCause() { return cause; }
    public void setCause(String cause) { this.cause = cause; }
    public String getPenalite() { return penalite; }
    public void setPenalite(String penalite) { this.penalite = penalite; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
