package com.school.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.management.entity.Eleve;
import com.school.management.entity.Parent;
import java.util.List;

public class ParentDTO {
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    @JsonIgnoreProperties({"payments", "notes", "parents"})
    private List<Eleve> eleves;
    private String statut;

    public ParentDTO() {}

    public ParentDTO(Long id, String nom, String adresse, String telephone, String email, List<Eleve> eleves, String statut) {
        this.id = id; this.nom = nom; this.adresse = adresse; this.telephone = telephone; this.email = email;
        this.eleves = eleves; this.statut = statut;
    }

    public static ParentDTO fromEntity(Parent p) {
        return new ParentDTO(p.getId(), p.getNom(), p.getAdresse(), p.getTelephone(), p.getEmail(), p.getEleves(), p.getStatut());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Eleve> getEleves() { return eleves; }
    public void setEleves(List<Eleve> eleves) { this.eleves = eleves; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
