package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Classe;
import com.school.management.entity.Eleve;
import com.school.management.entity.Matiere;
import com.school.management.entity.Note;
import java.time.LocalDate;

public class NoteDTO {
    private Long id;
    @JsonIgnoreProperties({"payments", "notes", "parents"})
    private Eleve eleve;
    @JsonIgnoreProperties({"enseignant", "classe"})
    private Matiere matiere;
    private Classe classe;
    private String type;
    private double valeur;
    private double coefficient;
    private LocalDate date;
    private String statut;

    public NoteDTO() {}

    public NoteDTO(Long id, Eleve eleve, Matiere matiere, Classe classe, String type, double valeur, double coefficient, LocalDate date, String statut) {
        this.id = id; this.eleve = eleve; this.matiere = matiere; this.classe = classe;
        this.type = type; this.valeur = valeur; this.coefficient = coefficient; this.date = date; this.statut = statut;
    }

    public static NoteDTO fromEntity(Note n) {
        return new NoteDTO(n.getId(), n.getEleve(), n.getMatiere(), n.getClasse(),
                n.getType(), n.getValeur(), n.getCoefficient(), n.getDate(), n.getStatut());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Eleve getEleve() { return eleve; }
    public void setEleve(Eleve eleve) { this.eleve = eleve; }
    public Matiere getMatiere() { return matiere; }
    public void setMatiere(Matiere matiere) { this.matiere = matiere; }
    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getValeur() { return valeur; }
    public void setValeur(double valeur) { this.valeur = valeur; }
    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
