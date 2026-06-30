package com.school.management.dto;

import com.school.management.entity.Classe;
import java.util.List;

public class ClasseDTO {
    private Long id;
    private String nom;
    private String niveau;
    private String filiere;
    private int effectif;
    private String anneeScolaire;
    private List<Long> eleveIds;
    private List<Long> matiereIds;

    public ClasseDTO() {}

    public ClasseDTO(Long id, String nom, String niveau, String filiere, int effectif, String anneeScolaire) {
        this.id = id; this.nom = nom; this.niveau = niveau; this.filiere = filiere;
        this.effectif = effectif; this.anneeScolaire = anneeScolaire;
    }

    public static ClasseDTO fromEntity(Classe c) {
        return new ClasseDTO(c.getId(), c.getNom(), c.getNiveau(), c.getFiliere(), c.getEffectif(), c.getAnneeScolaire());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
    public int getEffectif() { return effectif; }
    public void setEffectif(int effectif) { this.effectif = effectif; }
    public String getAnneeScolaire() { return anneeScolaire; }
    public void setAnneeScolaire(String anneeScolaire) { this.anneeScolaire = anneeScolaire; }
    public List<Long> getEleveIds() { return eleveIds; }
    public void setEleveIds(List<Long> eleveIds) { this.eleveIds = eleveIds; }
    public List<Long> getMatiereIds() { return matiereIds; }
    public void setMatiereIds(List<Long> matiereIds) { this.matiereIds = matiereIds; }
}
