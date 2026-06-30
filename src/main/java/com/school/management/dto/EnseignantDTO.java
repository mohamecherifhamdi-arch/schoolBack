package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Enseignant;
import com.school.management.entity.Matiere;
import java.util.List;

public class EnseignantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    @JsonIgnoreProperties("enseignant")
    private List<Matiere> matieres;
    private String statut;

    public EnseignantDTO() {}

    public EnseignantDTO(Long id, String nom, String prenom, String email, String telephone, List<Matiere> matieres, String statut) {
        this.id = id; this.nom = nom; this.prenom = prenom; this.email = email;
        this.telephone = telephone; this.matieres = matieres; this.statut = statut;
    }

    public static EnseignantDTO fromEntity(Enseignant e) {
        return new EnseignantDTO(e.getId(), e.getNom(), e.getPrenom(), e.getEmail(), e.getTelephone(), e.getMatieres(), e.getStatut());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public List<Matiere> getMatieres() { return matieres; }
    public void setMatieres(List<Matiere> matieres) { this.matieres = matieres; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
