package com.school.management.dto;

import com.school.management.entity.Eleve;
import java.time.LocalDate;

public class EleveDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String classe;
    private String niveau;
    private String telephone;
    private String email;
    private String statut;

    public EleveDTO() {}

    public EleveDTO(Long id, String nom, String prenom, String classe, String niveau, String telephone, String email, String statut) {
        this.id = id; this.nom = nom; this.prenom = prenom; this.classe = classe;
        this.niveau = niveau; this.telephone = telephone; this.email = email; this.statut = statut;
    }

    public static EleveDTO fromEntity(Eleve e) {
        return new EleveDTO(e.getId(), e.getNom(), e.getPrenom(), e.getClasse(), e.getNiveau(), e.getTelephone(), e.getEmail(), e.getStatut());
    }

    public Eleve toEntity() {
        Eleve e = new Eleve();
        e.setId(id); e.setNom(nom); e.setPrenom(prenom); e.setClasse(classe);
        e.setNiveau(niveau); e.setTelephone(telephone); e.setEmail(email); e.setStatut(statut);
        return e;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
