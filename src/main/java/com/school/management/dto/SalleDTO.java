package com.school.management.dto;

import com.school.management.entity.Salle;
import java.util.List;

public class SalleDTO {
    private Long id;
    private String code;
    private String nom;
    private int capacite;
    private String batiment;
    private int etage;
    private String statut;
    private List<String> equipement;

    public SalleDTO() {}

    public SalleDTO(Long id, String code, String nom, int capacite, String batiment, int etage, String statut, List<String> equipement) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.capacite = capacite;
        this.batiment = batiment;
        this.etage = etage;
        this.statut = statut;
        this.equipement = equipement;
    }

    public static SalleDTO fromEntity(Salle s) {
        return new SalleDTO(s.getId(), s.getCode(), s.getNom(), s.getCapacite(), s.getBatiment(), s.getEtage(), s.getStatut(), s.getEquipement());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public String getBatiment() { return batiment; }
    public void setBatiment(String batiment) { this.batiment = batiment; }
    public int getEtage() { return etage; }
    public void setEtage(int etage) { this.etage = etage; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public List<String> getEquipement() { return equipement; }
    public void setEquipement(List<String> equipement) { this.equipement = equipement; }
}
