package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Classe;
import com.school.management.entity.Enseignant;
import com.school.management.entity.Matiere;

public class MatiereDTO {
    private Long id;
    private String code;
    private String nom;
    private String niveau;
    @JsonIgnoreProperties({"matieres", "absences"})
    private Enseignant enseignant;
    @JsonIgnoreProperties({"eleves", "matieres"})
    private Classe classe;
    private int coefficient;
    private int credit;
    private int heures;
    private String statut;

    public MatiereDTO() {}

    public MatiereDTO(Long id, String code, String nom, String niveau, Enseignant enseignant, Classe classe, int coefficient, int credit, int heures, String statut) {
        this.id = id; this.code = code; this.nom = nom; this.niveau = niveau;
        this.enseignant = enseignant; this.classe = classe;
        this.coefficient = coefficient; this.credit = credit; this.heures = heures; this.statut = statut;
    }

    public static MatiereDTO fromEntity(Matiere m) {
        return new MatiereDTO(m.getId(), m.getCode(), m.getNom(), m.getNiveau(),
                m.getEnseignant(), m.getClasse(),
                m.getCoefficient(), m.getCredit(), m.getHeures(), m.getStatut());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }
    public Classe getClasse() { return classe; }
    public void setClasse(Classe classe) { this.classe = classe; }
    public int getCoefficient() { return coefficient; }
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
    public int getHeures() { return heures; }
    public void setHeures(int heures) { this.heures = heures; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
