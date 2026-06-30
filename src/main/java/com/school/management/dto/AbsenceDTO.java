package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Absence;
import com.school.management.entity.Enseignant;
import java.time.LocalDate;

public class AbsenceDTO {
    private Long id;
    @JsonIgnoreProperties({"matieres", "absences"})
    private Enseignant enseignant;
    private String classe;
    private String salle;
    private LocalDate date;
    private String seance;
    private boolean justifie;
    private String motif;

    public AbsenceDTO() {}

    public AbsenceDTO(Long id, Enseignant enseignant, String classe, String salle, LocalDate date, String seance, boolean justifie, String motif) {
        this.id = id; this.enseignant = enseignant; this.classe = classe; this.salle = salle;
        this.date = date; this.seance = seance; this.justifie = justifie; this.motif = motif;
    }

    public static AbsenceDTO fromEntity(Absence a) {
        return new AbsenceDTO(a.getId(), a.getEnseignant(),
                a.getClasse(), a.getSalle(), a.getDate(), a.getSeance(), a.isJustifie(), a.getMotif());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public String getSalle() { return salle; }
    public void setSalle(String salle) { this.salle = salle; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getSeance() { return seance; }
    public void setSeance(String seance) { this.seance = seance; }
    public boolean isJustifie() { return justifie; }
    public void setJustifie(boolean justifie) { this.justifie = justifie; }
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
}
